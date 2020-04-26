import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.classic.filter.LevelFilter
import ch.qos.logback.core.ConsoleAppender

import static ch.qos.logback.classic.Level.WARN
import static ch.qos.logback.core.spi.FilterReply.ACCEPT
//see  http://logback.qos.ch/manual/groovy.html
//每分钟扫描一次日志配置
scan()

def log_home = System.getProperty("LOG_HOME");

if(log_home == null){
    if(System.getProperty("os.name").contains("Windows")){
        log_home = "d:/wwwlogs/xxsystem/";
    }else{
        log_home = "/data/wwwlogs/xxsystem/";
    }
}

println "log home ==> ${log_home}"

/***控制台日志追加器配置***/
appender("CONSOLE", ConsoleAppender) {
    /**编码器**/
    encoder(PatternLayoutEncoder) {
        //日志时间-日志级别-线程-类-代码行
        pattern = "%d{yyyy/MM/dd-HH:mm:ss} %-5level [%thread] %class{5}:%line>>%msg%n"
    }
}


def byMinutes  = timestamp("yyyy-MM-dd'T'HH-mm")
//文件日志配置
appender("FILE", RollingFileAppender) {
    append = true;
    //给新产生的log文件命
    file = "${log_home}/${byMinutes}.log";
    encoder(PatternLayoutEncoder) {
        pattern = "%d{yyyy-MM-dd HH:mm:ss.SSS} [%thread] %-5level %logger{80} - %msg%n"
    }

    filter(LevelFilter) {
        level = WARN
        onMatch = ACCEPT
        //onMismatch = DENY
    }

    //移动文件以及对文件改名
    rollingPolicy(TimeBasedRollingPolicy){
        //分隔出来文件的命名模式
        fileNamePattern = "${log_home}/%d{yyyy-MM-dd}.log";
        //保留时间，超过这个时间的日志文件会自动删除
        maxHistory = 15;
    }

    // 日志文件达到指定大小触发滚动
    triggeringPolicy(SizeBasedTriggeringPolicy){
        maxFileSize = "20mb";
    }
}

root(WARN, ["FILE"])
