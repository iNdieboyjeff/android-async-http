package com.loopj.android.http.sample;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.BaseJsonHttpResponseHandler;
import com.loopj.android.http.sample.util.SampleJSON;

import org.apache.http.Header;

public class JsonSample extends SampleParentActivity {

    private static final String LOG_TAG = "JsonSample";

    @Override
    protected void executeSample(AsyncHttpClient client, String URL, AsyncHttpResponseHandler responseHandler) {
        client.get(this, URL, responseHandler);
    }

    @Override
    protected int getSampleTitle() {
        return R.string.title_json_sample;
    }

    @Override
    protected boolean isRequestBodyAllowed() {
        return false;
    }

    @Override
    protected boolean isRequestHeadersAllowed() {
        return false;
    }

    @Override
    protected String getDefaultURL() {
        return "http://www.jsonip.com/";
    }

    @Override
    protected AsyncHttpResponseHandler getResponseHandler() {
        return new BaseJsonHttpResponseHandler<SampleJSON>() {

            @Override
            public void onStart() {
                clearOutputs();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String rawResponse, SampleJSON response) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                if (response != null) {
                    debugResponse(LOG_TAG, rawResponse);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable e, String rawResponse, SampleJSON errorResponse) {
                debugHeaders(LOG_TAG, headers);
                debugStatusCode(LOG_TAG, statusCode);
                debugThrowable(LOG_TAG, e);
                if (errorResponse != null) {
                    debugResponse(LOG_TAG, rawResponse);
                }
            }

            @Override
            protected SampleJSON parseResponse(String responseBody) throws Throwable {
                return new ObjectMapper().readValues(new JsonFactory().createParser(responseBody), SampleJSON.class).next();
            }
        };
    }
}
