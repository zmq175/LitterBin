package info.chengzhi.chengzhi_litter_bin.app.api.response;

import com.alibaba.fastjson.JSON;
import info.chengzhi.chengzhi_litter_bin.app.domain.utils.TraceIdUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class Response implements ApiResult {

  private RespMeta meta;
  private Map<String, Object> data;
  private RespPagination pagination;
  private String traceId;

  public Response() {

  }

  public Response(HttpServletRequest request, int errCode) {
    request.setAttribute("errCode", errCode);
  }

  public Response addDate(String name, Object object) {
    if (data == null) {
      data = new HashMap<>();
    }
    data.put(name, object);
    return this;
  }

  public int getCode() {
    return getMetaOptional().map(RespMeta::getCode).orElse(-1);
  }

  private Optional<RespMeta> getMetaOptional() {
    return Optional.ofNullable(meta);
  }

  @Override
  public boolean isSuccess() {
    int code = Optional.ofNullable(getMeta()).map(RespMeta::getCode).orElse(0);
    return code == 0 || code == 200;
  }

  @Override
  public void setTraceId(String traceId) {
    this.traceId = traceId;
  }

  @Override
  public String getTraceId() {
    return Optional.ofNullable(this.traceId).orElse(TraceIdUtils.getTraceId());
  }

  public RespMeta getMeta() {
    return meta;
  }

  public void setMeta(RespMeta meta) {
    this.meta = meta;
  }

  public Map<String, Object> getData() {
    return data;
  }

  public void setData(Map<String, Object> data) {
    this.data = data;
  }

  public RespPagination getPagination() {
    return pagination;
  }

  public void setPagination(RespPagination pagination) {
    this.pagination = pagination;
  }

  public void flush(HttpServletResponse response) {
    PrintWriter printWriter = null;
    response.setCharacterEncoding("UTF-8");
    response.setContentType("application/json;charset=UTF-8");
    try {
      printWriter = response.getWriter();
      printWriter.print(JSON.toJSONString(this));
    } catch (IOException ex) {

    } finally {
      if (Objects.nonNull(printWriter)) {
        printWriter.flush();
        printWriter.close();
      }
    }
  }
}
