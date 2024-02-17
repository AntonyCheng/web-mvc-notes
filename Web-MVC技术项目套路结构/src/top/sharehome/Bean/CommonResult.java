package top.sharehome.Bean;

public class CommonResult {
    /**
     * 表示服务器处理资源的标志
     */
    private boolean flag;
    /**
     * 当服务器处理请求成功的时候要显示给客户端的数据
     */
    private Object resultData;
    /**
     * 当服务器处理请求失败的时候要相应给客户的错误信息
     */
    private String message;

    /**
     * 处理请求成功
	 * 该方法的返回值经常搭配下方setResultData(结果数据)方法使用，以便传送服务端给客户端的数据
     *
     * @return
     */
    public static CommonResult ok() {
        return new CommonResult().setFlag(true);
    }

    /**
     * 处理请求失败
     *
     * @return
     */
    public static CommonResult error() {
        return new CommonResult().setFlag(false);
    }

    public boolean isFlag() {
        return flag;
    }

    private CommonResult setFlag(boolean flag) {
        this.flag = flag;
        return this;
    }

    public Object getResultData() {
        return resultData;
    }

    public CommonResult setResultData(Object resultData) {
        this.resultData = resultData;
        return this;
    }

    public String getMessage() {
        return message;
    }

    public CommonResult setMessage(String message) {
        this.message = message;
        return this;
    }

    @Override
    public String toString() {
        return "CommonResult{" +
                "flag=" + flag +
                ", resultData=" + resultData +
                ", message='" + message + '\'' +
                '}';
    }
}
