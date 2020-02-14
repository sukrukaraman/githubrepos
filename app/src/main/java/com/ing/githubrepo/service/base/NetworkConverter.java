package com.ing.githubrepo.service.base;


import java.lang.reflect.Type;

/**
 * Created by karamans on 15.02.2019.
 */
public interface NetworkConverter<T> {
   <T> T fromBody(String s, Type type);
   String toBody(T object);
}
