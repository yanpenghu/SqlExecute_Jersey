package com.egova.heroku.SqlExecute_Jersey.utils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import com.google.gson.Gson;

public abstract class GsonKit {
	private static Gson GSON = new Gson();

	public static String toJson(Object source) {
		return GSON.toJson(source);
	}

	public static <T> T from(String source, Class<T> t) {
		return GSON.fromJson(source, t);
	}

	public static <T> T from(String source, ParameterizedType type) {
		return GSON.fromJson(source, type);
	}	

	public static ParameterizedType type(final Class<?> raw, final Type... args) {
		return new ParameterizedType() {
			public Type getRawType() {
				return raw;
			}

			public Type[] getActualTypeArguments() {
				return args;
			}

			public Type getOwnerType() {
				return null;
			}
		};
	}
}
