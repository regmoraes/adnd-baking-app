/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.regmoraes.bakingapp.presentation;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * A generic class that holds a value with its loading status.
 * @param <T>
 */
public class ViewModelResponse<T> {

    /**
     * Possible status types of a response provided to the UI. <br><br>
     *
     * This class was created by James Shvarts and presented his article
     * <a href="https://proandroiddev.com/mvvm-architecture-using-livedata-rxjava-and-new-dagger-android-injection-639837b1eb6c">"Implementing MVVM using LiveData, RxJava, Dagger Android"</a>
     */
    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }

    @NonNull
    public final Status status;

    @Nullable
    public final String message;

    @Nullable
    public final T data;

    public ViewModelResponse(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public static <T> ViewModelResponse<T> success(@Nullable T data) {
        return new ViewModelResponse<>(Status.SUCCESS, data, null);
    }

    public static <T> ViewModelResponse<T> error(String msg, @Nullable T data) {
        return new ViewModelResponse<>(Status.ERROR, data, msg);
    }

    public static <T> ViewModelResponse<T> loading(@Nullable T data) {
        return new ViewModelResponse<>(Status.LOADING, data, null);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ViewModelResponse<?> resource = (ViewModelResponse<?>) o;

        if (status != resource.status) {
            return false;
        }
        if (message != null ? !message.equals(resource.message) : resource.message != null) {
            return false;
        }
        return data != null ? data.equals(resource.data) : resource.data == null;
    }

    @Override
    public int hashCode() {
        int result = status.hashCode();
        result = 31 * result + (message != null ? message.hashCode() : 0);
        result = 31 * result + (data != null ? data.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ViewModelResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
