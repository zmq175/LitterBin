package info.chengzhi.chengzhi_litter_bin.app.api.response;

public interface ApiResult {
  boolean isSuccess();

  void setTraceId(String traceId);

  String getTraceId();
}
