# 模拟 OAuth 2.0   added by 冷鹏

OAuth 2.0 规定了四种获得令牌的流程。
你可以选择最适合自己的那一种，向第三方应用颁发令牌。下面就是这四种授权方式。
授权码（authorization-code）
隐藏式（implicit）
密码式（password）：
客户端凭证（client credentials）

OAuth运行流程
（A）用户打开客户端以后，客户端要求用户给予授权。
（B）用户同意给予客户端授权。
（C）客户端使用上一步获得的授权，向认证服务器申请令牌。
（D）认证服务器对客户端进行认证以后，确认无误，同意发放令牌。
（E）客户端使用令牌，向资源服务器申请获取资源。
（F）资源服务器确认令牌无误，同意向客户端开放资源。


1.  http://localhost:7001/login   登陆
2.  豆瓣登陆，下面是 QQ授权登陆 按钮
3.  http://192.168.102.20:7001/leadToAuthorize 请求QQ的服务
4.  http://192.168.102.20:7000/authorize?response_type=code&client_id=10000032&redirect_uri=http://192.168.102.20:7001/index&scope=userinfo&state=hehe
5.  QQ的服务接收authorize方法跳转QQ的登陆界面
            @RequestMapping("authorize")
          public String authorize(String response_type, String client_id, String redirect_uri, String scope, String state){

              return "login";
          }
6.  输入用户名和密码点击授权登录  window.location.href=path + ":7000/login";
    /**
     * （C）假设用户给予授权，认证服务器将用户导向客户端事先指定的"重定向URI"（redirection URI），同时附上一个授权码。
     */
    @RequestMapping("login")
    public void login(String username, String password, HttpServletResponse response) throws IOException {
        // 验证用户名密码是否正确
        response.sendRedirect(getRedirect_uri() + "?code=xxx&state=hehe");
    }
7.  访问http://192.168.102.20:7001/index?code=xxx&state=hehe 调豆瓣的服务。这一步是将QQ给的code带上作为参数再去调QQ的服务
    获取token  getTokenByCode
 @RequestMapping("index")
    public String index(String code, HttpServletRequest request) throws Exception {
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        /**
         * （D）客户端收到授权码，附上早先的"重定向URI"，向认证服务器申请令牌。这一步是在客户端的后台的服务器上完成的，对用户不可见。
         */
        String accessToken = restTemplate.getForObject("http://"+ getLocalHost() +":7000/getTokenByCode?" +
            "grant_type=authorization_code&" +
            "code=xxx&" +
            "redirect_uri=http://"+ getLocalHost() +":7001/index", String.class);
        /**
         * 发起通过token换用户信息的请求.这一步调QQ的服务 资源服务器确认令牌无误，同意向客户端开放资源。。
         */
        String username = restTemplate.getForObject("http://"+ getLocalHost() +":7000/getUserinfoByToken?" +
            "access_token=yyy", String.class);

        request.getSession().setAttribute("username",username);

        return "index";
    }