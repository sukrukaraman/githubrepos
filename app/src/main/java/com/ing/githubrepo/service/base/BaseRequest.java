package com.ing.githubrepo.service.base;

import android.net.Uri;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by karamans on 13.02.2020.
 */

public abstract class BaseRequest<RequestModel extends BaseRequestModel, ResponseModel>
        extends Request<ResponseModel> {

    protected RequestModel requestModel;
    private RetryPolicy retryPolicy;
    protected ResponseListener<ResponseModel> responseListener;
    protected ErrorListener errorListener;
    private Map<String, String> extraQueryParameters;
    private List<String> extraPathParameters;

    public BaseRequest() {
        super(1, null, null);
        this.extraQueryParameters = new HashMap();
        this.extraPathParameters = new ArrayList();
        this.setRetryPolicy(this.getRetryPolicy());
    }

    public BaseRequest(ResponseListener<ResponseModel> responseListener, ErrorListener errorListener) {
        this();
        this.responseListener = responseListener;
        this.errorListener = errorListener;
    }

    public String getBaseUrl() {
        return NetworkStack.getInstance().getUrl();
    }

    @Override
    public int getMethod() {
        return Method.GET;
    }

    @Override
    public String getUrl() {
        String baseUrl = this.getBaseUrl();
        String actionUrl = this.getActionUrl();
        return this.getFormattedUrl(baseUrl + actionUrl);
    }

    private String getFormattedUrl(String currentUrl) {
        Uri.Builder paramBuilder = Uri.parse(currentUrl).buildUpon();
        Iterator i$ = this.extraQueryParameters.entrySet().iterator();

        while (i$.hasNext()) {
            Map.Entry<String, String> parameter = (Map.Entry) i$.next();
            paramBuilder.appendQueryParameter(parameter.getKey(), parameter.getValue());
        }

        i$ = this.extraPathParameters.iterator();

        while (i$.hasNext()) {
            String parameter = (String) i$.next();
            paramBuilder.appendPath(parameter);
        }

        return paramBuilder.build().toString();
    }

    public int getMaxRetryCount() {
        return NetworkStack.getInstance().getMaxRetryCount();
    }

    public int getTimeout() {
        return NetworkStack.getInstance().getTimeOutInMillis();
    }

    public float getBackOffMultiplier() {
        return NetworkStack.getInstance().getBackOffMultiplier();
    }

    public RetryPolicy getRetryPolicy() {
        if (null == this.retryPolicy) {
            this.retryPolicy = new DefaultRetryPolicy(this.getTimeout(), this.getMaxRetryCount(), this.getBackOffMultiplier());
        }

        return this.retryPolicy;
    }

    protected Response<ResponseModel> parseNetworkResponse(NetworkResponse response) {
        try {
            String rawAsString = new String(response.data, "UTF-8");

            Log.d(getClass().getSimpleName() + "==>", rawAsString);

            Gson converter = new Gson();
            ResponseModel result = converter.fromJson(rawAsString, getResponseModelClassType());

            return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
        } catch (Exception ex) {
            Log.d("BaseRequest-->", ex.getMessage());
            return Response.error(new VolleyError(ex));
        }
    }


    @Override
    protected void deliverResponse(ResponseModel response) {
        if (responseListener != null) {
            responseListener.onResponse(response);
        }
    }

    public void deliverError(VolleyError error) {
        if (null != error) {
            if (this.errorListener != null) {
                this.errorListener.onErrorResponse(error);
            }
        }
    }

    public abstract Type getResponseModelClassType();

    public abstract String getActionUrl();

    public interface ResponseListener<ResponseModel> extends Response.Listener<ResponseModel> {
        void onResponse(ResponseModel responseModel);
    }

    public interface ErrorListener extends Response.ErrorListener {
    }
}