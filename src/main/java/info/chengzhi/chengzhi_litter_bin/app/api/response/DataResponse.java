package info.chengzhi.chengzhi_litter_bin.app.api.response;

import info.chengzhi.chengzhi_litter_bin.app.domain.utils.TraceIdUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

public class DataResponse<T> implements ApiResult {
  private RespMeta meta;
  private T data;
  private RespPagination pagination;
  private String traceId;

  public DataResponse() {

  }

  public DataResponse(HttpServletRequest request, int errno) {
    request.setAttribute("errno", errno);
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

  private RespMeta getMeta() {
    return meta;
  }

  public void setMeta(RespMeta meta) {
    this.meta = meta;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

  public RespPagination getPagination() {
    return pagination;
  }

  public void setPagination(RespPagination pagination) {
    this.pagination = pagination;
  }
}
