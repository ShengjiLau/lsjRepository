
if ngx.var.cookie_cwms_ticket == nil then
    --ngx.print(ngx.var.host .. ngx.var.port .. '/' .. ngx.var.request_uri)
    backurl = ngx.escape_uri("http://" .. ngx.req.get_headers()["Host"] .. '/' .. ngx.var.request_uri)     
    ngx.redirect("http://39.107.12.215:52412/account/?authCallback=" .. backurl, 302)
end
ngx.exit(ngx.OK)
return      

