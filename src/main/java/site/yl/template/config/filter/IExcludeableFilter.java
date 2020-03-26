package site.yl.template.config.filter;

public interface IExcludeableFilter {

  String getExclude(String pathPattern);

  void  doExclude();
}
