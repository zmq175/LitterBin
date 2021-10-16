package info.chengzhi.chengzhi_litter_bin.app.api.response;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public class Responses {
  public static final int DEFAULT_ERROR_CODE = 500;

  private static final String DEFAULT_RESULT_DATA_KEY = "result";

  public static Response successResponse(HttpServletRequest request) {
    Response response = new Response(request, 0);

    RespMeta meta = new RespMeta();
    meta.setCode(0);
    response.setMeta(meta);

    return response;
  }

  public static Response successResponse(HttpServletRequest request, Map data) {
    Response response = new Response(request, 0);

    RespMeta meta = new RespMeta();
    meta.setCode(0);
    response.setMeta(meta);
    response.setData(data);

    return response;
  }

  public static <T>  DataResponse successResponse(HttpServletRequest request, T data) {
    DataResponse<T> response = new DataResponse<>(request, 0);
    RespMeta meta = new RespMeta();
    meta.setCode(0);
    response.setMeta(meta);
    response.setData(data);

    return response;
  }

  public static <T> DataResponse errorResponse(T data, String errMsg) {
    DataResponse<T> response = new DataResponse<>();
    RespMeta meta = new RespMeta();
    meta.setCode(1);
    meta.setErrorMsg(errMsg);
    response.setMeta(meta);
    response.setData(data);

    return response;
  }

  public static Response errorResponse(HttpServletRequest request, Exception ex) {
    return errorResponse(request, ex.toString());
  }

  public static Response errorResponse(HttpServletRequest request, String errorMsg) {
    return errorResponse(request, 1, errorMsg);
  }

  public static Response errorResponse(HttpServletRequest request, int code, String errorMsg) {
    RespMeta meta = new RespMeta();
    meta.setCode(code);
    meta.setErrorType("UNKNOWN");
    meta.setErrorMsg(errorMsg);

    Response response = new Response(request, 1);
    response.setMeta(meta);

    return response;
  }


}
