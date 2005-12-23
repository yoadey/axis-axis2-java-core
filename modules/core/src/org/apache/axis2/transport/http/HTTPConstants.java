/*
* Copyright 2004,2005 The Apache Software Foundation.
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


package org.apache.axis2.transport.http;

import java.io.UnsupportedEncodingException;

/**
 * HTTP protocol and message context constants.
 */
public class HTTPConstants {

    public static final String HTTP_CONTENT_TYPE = "HTTP_CONTENT_TYPE";
    public static final String PROTOCOL_VERSION = "PROTOCOL";
    public static final String MEDIA_TYPE_X_WWW_FORM =
            "application/x-www-form-urlencoded";
    public static final String MEDIA_TYPE_TEXT_XML = "text/xml";
    public static final String MEDIA_TYPE_MULTIPART_RELATED = "multipart/related";
    public static final String MEDIA_TYPE_APPLICATION_XML = "application/xml";
    public static final String MEDIA_TYPE_APPLICATION_SOAP_XML = "application/soap+xml";

    /**
     * Field REQUEST_URI
     */
    public static final String REQUEST_URI = "REQUEST_URI";

    /**
     * Field RESPONSE_CODE
     */
    public static final String RESPONSE_CODE = "RESPONSE_CODE";

    /**
     * Field RESPONSE_WORD
     */
    public static final String RESPONSE_WORD = "RESPONSE_WORD";

    /**
     * Field RESPONSE_ACK_CODE_VAL
     */
    public static final String RESPONSE_ACK_CODE_VAL = "202";

    /**
     * Field SOCKET
     */
    public static final String SOCKET = "SOCKET";

    /**
     * Field RESPONSE_HEADERS
     */
    public static final String RESPONSE_HEADERS = "HTTP-Response-Headers";

    /**
     * Field REQUEST_HEADERS
     */
    public static final String REQUEST_HEADERS = "HTTP-Request-Headers";

    /**
     * Field PLUGIN_WRITER
     */
    public static final String PLUGIN_WRITER = "transport.http.plugin.writer";

    /**
     * Field PLUGIN_SERVICE_NAME
     */
    public static final String PLUGIN_SERVICE_NAME = "transport.http.plugin.serviceName";

    /**
     * AXIS servlet plugin parameter names.
     */
    public static final String PLUGIN_NAME = "transport.http.plugin.pluginName";

    /**
     * Field PLUGIN_LOG
     */
    public static final String PLUGIN_LOG = "transport.http.plugin.log";

    /**
     * Field PLUGIN_IS_DEVELOPMENT
     */
    public static final String PLUGIN_IS_DEVELOPMENT = "transport.http.plugin.isDevelopment";

    /**
     * Field PLUGIN_EXCEPTION_LOG
     */
    public static final String PLUGIN_EXCEPTION_LOG = "transport.http.plugin.exceptionLog";

    /**
     * Field PLUGIN_ENGINE
     */
    public static final String PLUGIN_ENGINE = "transport.http.plugin.engine";

    /**
     * Field PLUGIN_ENABLE_LIST
     */
    public static final String PLUGIN_ENABLE_LIST = "transport.http.plugin.enableList";

    /**
     * Field OK[]
     */
    public static final char OK[] = ("200 OK").toCharArray();

    /**
     * Field NOCONTENT[]
     */
    public static final byte NOCONTENT[] = ("202 OK\n\n").getBytes();

    /**
     * String
     */
    public static String MC_HTTP_STATUS_MESSAGE = "transport.http.statusMessage";

    /**
     * Integer
     */
    public static String MC_HTTP_STATUS_CODE = "transport.http.statusCode";

    /**
     * HttpServletResponse
     */
    public static String MC_HTTP_SERVLETRESPONSE = "transport.http.servletResponse";

    /**
     * HttpServletRequest
     */
    public static String MC_HTTP_SERVLETREQUEST = "transport.http.servletRequest";

    /**
     * Field MC_HTTP_SERVLETPATHINFO
     */
    public static String MC_HTTP_SERVLETPATHINFO = "transport.http.servletPathInfo";

    /**
     * Field MC_HTTP_SERVLETLOCATION
     */
    public static String MC_HTTP_SERVLETLOCATION = "transport.http.servletLocation";

    /**
     * HttpServlet
     */
    public static String MC_HTTP_SERVLET = "transport.http.servlet";

    /**
     * Field HEADER_USER_AGENT
     */
    public static final String HEADER_USER_AGENT = "User-Agent";

    /**
     * Field HEADER_TRANSFER_ENCODING_CHUNKED
     */
    public static final String HEADER_TRANSFER_ENCODING_CHUNKED = "chunked".intern();

    /* http 1.1 */

    /**
     * Field HEADER_TRANSFER_ENCODING
     */
    public static final String HEADER_TRANSFER_ENCODING = "Transfer-Encoding".intern();

    /**
     * Field HEADER_SOAP_ACTION
     */
    public static final String HEADER_SOAP_ACTION = "SOAPAction";

    /**
     * Field HEADER_SET_COOKIE2
     */
    public static final String HEADER_SET_COOKIE2 = "Set-Cookie2";

    /**
     * Field HEADER_SET_COOKIE
     */
    public static final String HEADER_SET_COOKIE = "Set-Cookie";

    /**
     * Field HEADER_PROXY_AUTHORIZATION
     */
    public static final String HEADER_PROXY_AUTHORIZATION = "Proxy-Authorization";

    /**
     * Field HEADER_PROTOCOL_V11
     */
    public static final String HEADER_PROTOCOL_V11 = "1.1".intern();

    /**
     * Field HEADER_PROTOCOL_V10
     */
    public static final String HEADER_PROTOCOL_V10 = "1.0".intern();

    /**
     * Field HEADER_PROTOCOL_11
     */
    public static final String HEADER_PROTOCOL_11 = "HTTP/1.1";

    /**
     * Field HEADER_PROTOCOL_10
     */
    public static final String HEADER_PROTOCOL_10 = "HTTP/1.0";

    /**
     * Field HEADER_PRAGMA
     */
    public static final String HEADER_PRAGMA = "Pragma";

    /**
     * Field HEADER_POST
     */
    public static final String HEADER_POST = "POST";

    /**
     * Field HEADER_LOCATION
     */
    public static final String HEADER_LOCATION = "Location";

    /**
     * Field HEADER_HOST
     */
    public static final String HEADER_HOST = "Host";

    /**
     * Field HEADER_GET
     */
    public static final String HEADER_GET = "GET";

    /**
     * Field HEADER_EXPECT_100_Continue
     */
    public static final String HEADER_EXPECT_100_Continue = "100-continue";

    /**
     * Field HEADER_EXPECT
     */
    public static final String HEADER_EXPECT = "Expect";

    /**
     * HTTP header field values
     */
    public static final String HEADER_DEFAULT_CHAR_ENCODING = "iso-8859-1";

    /**
     * Field HEADER_COOKIE2
     */
    public static final String HEADER_COOKIE2 = "Cookie2";

    /**
     * Cookie headers
     */
    public static final String HEADER_COOKIE = "Cookie";

    /**
     * Field HEADER_CONTENT_TYPE_JMS
     */
    public static final String HEADER_CONTENT_TYPE_JMS = "ContentType";

    /**
     * Field HEADER_CONTENT_TYPE
     */
    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    /**
     * Field HEADER_CONTENT_TRANSFER_ENCODING
     */
    public static final String HEADER_CONTENT_TRANSFER_ENCODING = "Content-Transfer-Encoding";

    /**
     * Field HEADER_CONTENT_LOCATION
     */
    public static final String HEADER_CONTENT_LOCATION = "Content-Location";

    /**
     * Field HEADER_CONTENT_LENGTH
     */
    public static final String HEADER_CONTENT_LENGTH = "Content-Length";

    /**
     * Field HEADER_CONTENT_ID
     */
    public static final String HEADER_CONTENT_ID = "Content-Id";

    /**
     * Field HEADER_CONTENT_DESCRIPTION
     */
    public static final String HEADER_CONTENT_DESCRIPTION = "Content-Description";

    /**
     * Field HEADER_CONNECTION_KEEPALIVE
     */
    public static final String HEADER_CONNECTION_KEEPALIVE = "Keep-Alive".intern();    

    /**
     * Field HEADER_CONNECTION_CLOSE
     */
    public static final String HEADER_CONNECTION_CLOSE = "close".intern();

    /**
     * Field HEADER_CONNECTION
     */
    public static final String HEADER_CONNECTION = "Connection";

    /**
     * Field HEADER_CACHE_CONTROL_NOCACHE
     */
    public static final String HEADER_CACHE_CONTROL_NOCACHE = "no-cache";

    /**
     * Field HEADER_CACHE_CONTROL
     */
    public static final String HEADER_CACHE_CONTROL = "Cache-Control";

    /**
     * Field HEADER_AUTHORIZATION
     */
    public static final String HEADER_AUTHORIZATION = "Authorization";

    /**
     * Field HEADER_ACCEPT_TEXT_ALL
     */
    public static final String HEADER_ACCEPT_TEXT_ALL = "text/*";

    /**
     * Field HEADER_ACCEPT_MULTIPART_RELATED
     */
    public static final String HEADER_ACCEPT_MULTIPART_RELATED = "multipart/related";

    /**
     * Field HEADER_ACCEPT_APPL_SOAP
     */
    public static final String HEADER_ACCEPT_APPL_SOAP = "application/soap+xml";

    /**
     * Field HEADER_ACCEPT_APPLICATION_DIME
     */
    public static final String HEADER_ACCEPT_APPLICATION_DIME = "application/dime";

    /**
     * Field HEADER_ACCEPT
     */
    public static final String HEADER_ACCEPT = "Accept";

    /**
     * Field CHAR_SET_ENCODING
     */
    public static String CHAR_SET_ENCODING = "charset";

    /**
     * Field UNAUTH[]
     */
    public static final byte UNAUTH[] = ("401 Unauthorized").getBytes();

    /**
     * Field SO_TIMEOUT
     */
    public static final String SO_TIMEOUT = "SO_TIMEOUT";

    /**
     * Field SENDER[]
     */
    public static final byte SENDER[] = "400".getBytes();
    public static final String PROXY = "PROXY";
    public static final String MTOM_RECIVED_CONTENT_TYPE = "MTOM_RECEIVED";

    /**
     * Field ISE[]
     */
    public static final byte ISE[] = ("500 Internal server error").getBytes();

    /**
     * Field HTTP_REQ_TYPE
     */
    public static final String HTTP_REQ_TYPE = "HTTP_REQ_TYPE";

    /**
     * Default content encoding chatset
     */
    public static final String HTTP_ELEMENT_CHARSET = "US-ASCII";

    /**
     * Field HTTP[]
     */
    public static char HTTP[] = "HTTP/1.0 ".toCharArray();

    /**
     * Field DEFAULT_SO_TIMEOUT
     */
    public static final int DEFAULT_SO_TIMEOUT = 60000;

    /**
     * Field DEFAULT_CONNECTION_TIMEOUT
     */
    public static final int DEFAULT_CONNECTION_TIMEOUT = 60000;

    /**
     * Field CONNECTION_TIMEOUT
     */
    public static final String CONNECTION_TIMEOUT = "CONNECTION_TIMEOUT";

    /**
     * Method getBytes
     *
     * @param data
     * @return
     */
    public static byte[] getBytes(final String data) {
        if (data == null) {
            throw new IllegalArgumentException("Parameter may not be null");
        }

        try {
            return data.getBytes(HTTP_ELEMENT_CHARSET);
        } catch (UnsupportedEncodingException e) {
        }

        return data.getBytes();
    }
}
