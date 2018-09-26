package com.devin.common.utils;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取IP方法
 * 
 * @author devin
 */
public class IpUtils
{
    public static String getIpAddr(HttpServletRequest request)
    {
        if (request == null)
        {
            return "unknown";
        }
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Forwarded-For");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getHeader("X-Real-IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
        {
            ip = request.getRemoteAddr();
        }

        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }
/**
  * ip地址转成long型数字
  * 将IP地址转化成整数的方法如下：
  * 1、通过String的split方法按.分隔得到4个长度的数组
  * 2、通过左移位操作（<<）给每一段的数字加权，第一段的权为2的24次方，第二段的权为2的16次方，第三段的权为2的8次方，最后一段的权为1
  * @param strIp
  * @return
  */
    public static long ipToLong(String strIp) {
        String[] ip = strIp.split("\\.");
        return (Long.parseLong(ip[0]) << 24) + (Long.parseLong(ip[1]) << 16) + (Long.parseLong(ip[2]) << 8) + Long.parseLong(ip[3]);
    }
}
