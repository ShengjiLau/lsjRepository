
if ngx.var.cookie_cwms_ticket == nil then
    --ngx.print(ngx.var.host .. ngx.var.port .. '/' .. ngx.var.request_uri)
    backurl = ngx.escape_uri("http://" .. ngx.req.get_headers()["Host"] .. '/' .. ngx.var.request_uri)     
    ngx.redirect("http://login.datuodui.com:52412/account/?auth_callback=" .. backurl, 302)
end
ngx.exit(ngx.OK)
return      

