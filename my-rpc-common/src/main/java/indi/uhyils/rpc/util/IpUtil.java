package indi.uhyils.rpc.util;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;
import org.apache.commons.lang3.StringUtils;

/**
 * @author uhyils <247452312@qq.com>
 * @date 文件创建日期 2020年06月19日 09时15分
 */
public class IpUtil {


    private static final String LEGAL_LOCAL_IP_PROPERTY = "java.net.preferIPv6Addresses";


    private static final String CLIENT_LOCAL_IP_PROPERTY = "indi.uhyils.rpc.ip";

    private static final String DEFAULT_SOLVE_FAILED_RETURN = "resolve_failed";

    private static final String CLIENT_LOCAL_PREFER_HOSTNAME_PROPERTY = "indi.uhyils.rpc.local.preferHostname";

    private static String localIp;

    public static String getIp() {
        if (!StringUtils.isEmpty(localIp)) {
            return localIp;
        }

        if (System.getProperties().containsKey(CLIENT_LOCAL_IP_PROPERTY)) {
            return localIp = System.getProperty(CLIENT_LOCAL_IP_PROPERTY, getAddress());
        }

        String ip = getAddress();

        return localIp = ip;

    }

    private static String getAddress() {
        InetAddress inetAddress = findFirstNonLoopbackAddress();
        if (inetAddress == null) {
            return DEFAULT_SOLVE_FAILED_RETURN;
        }

        boolean preferHost = Boolean.parseBoolean(System.getProperty(CLIENT_LOCAL_PREFER_HOSTNAME_PROPERTY));
        return preferHost ? inetAddress.getHostName() : inetAddress.getHostAddress();
    }

    private static InetAddress findFirstNonLoopbackAddress() {
        InetAddress result = null;

        try {
            int lowest = Integer.MAX_VALUE;
            for (Enumeration<NetworkInterface> nics = NetworkInterface.getNetworkInterfaces();
                nics.hasMoreElements(); ) {
                NetworkInterface ifc = nics.nextElement();
                if (ifc.isUp()) {
                    if (ifc.getIndex() < lowest || result == null) {
                        lowest = ifc.getIndex();
                    } else {
                        continue;
                    }

                    for (Enumeration<InetAddress> addrs = ifc.getInetAddresses(); addrs.hasMoreElements(); ) {
                        InetAddress address = addrs.nextElement();
                        boolean isLegalIpVersion =
                            Boolean.parseBoolean(System.getProperty(LEGAL_LOCAL_IP_PROPERTY))
                                ? address instanceof Inet6Address : address instanceof Inet4Address;
                        if (isLegalIpVersion && !address.isLoopbackAddress()) {
                            result = address;
                        }
                    }

                }
            }
        } catch (IOException ex) {
            //ignore
        }

        if (result != null) {
            return result;
        }

        try {
            return InetAddress.getLocalHost();
        } catch (UnknownHostException e) {
            //ignore
        }

        return null;

    }
}
