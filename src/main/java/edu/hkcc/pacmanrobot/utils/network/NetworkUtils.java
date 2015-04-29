package edu.hkcc.pacmanrobot.utils.network;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by beenotung on 4/29/15.
 */
public class NetworkUtils {
    public static int MAC_ADDRESS_BYTES = 6;

    public static InetAddress getOnlineInetAddress() throws SocketException {
        InetAddress result = null;
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface e = networkInterfaces.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            if (e.isLoopback() || e.isVirtual()) {
                // internal network interface
            } else {
                // external netwokr interface
                for (; a.hasMoreElements(); ) {
                    result = a.nextElement();
                    if (result instanceof Inet4Address)
                        //skip ipv6
                        return result;
                }
            }
        }
        throw new SocketException();
    }

    public static byte[] getMacAddress(InetAddress inetAddress) throws SocketException {
        byte[] result = null;
        for (Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces(); networkInterfaces.hasMoreElements(); ) {
            NetworkInterface e = networkInterfaces.nextElement();
            Enumeration<InetAddress> a = e.getInetAddresses();
            if (e.isLoopback() || e.isVirtual()) {
                // internal network interface
            } else
                // external netwokr interface
                return e.getByInetAddress(inetAddress).getHardwareAddress();
        }
        throw new SocketException();
    }

    public static byte[] getLocalMacAddress() throws SocketException {
        return getMacAddress(getOnlineInetAddress());
    }
}
