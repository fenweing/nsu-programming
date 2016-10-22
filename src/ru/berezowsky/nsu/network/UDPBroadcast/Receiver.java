package ru.berezowsky.nsu.network.UDPBroadcast;

import ru.berezowsky.nsu.network.Debugger;
import ru.berezowsky.nsu.network.Timer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.SocketAddress;
import java.util.concurrent.ConcurrentHashMap;

class Receiver extends Thread {

    private final UDPSocket socket;
    private final ConcurrentHashMap<SocketAddress, Integer> machinesOnline = new ConcurrentHashMap<>();
    private final Timer machinesChecker = new Timer();

    Receiver(UDPSocket socket) {
        this.socket = socket;
    }

    ConcurrentHashMap<SocketAddress, Integer> getMachinesOnline() {
        return machinesOnline;
    }

    @Override
    public void run() {

        machinesChecker.schedulePeriodicAction(()->{
            Debugger.log("\n\nMachines online:" + machinesOnline.size());
            machinesOnline.forEach((adr, count) -> {
                count--;
                if (count <= 0){
                    machinesOnline.remove(adr);
                }
                Debugger.log(adr);
            });
        }, 1);

        try {
            byte[] buf = new byte[10];
            DatagramPacket recvPacket = new DatagramPacket(buf, buf.length);

            while (!isInterrupted()) {
                socket.receive(recvPacket);
                SocketAddress adr = recvPacket.getSocketAddress();


                if (machinesOnline.get(adr) != null && machinesOnline.get(adr) < 3) {
                    machinesOnline.put(adr, machinesOnline.get(adr) + 1);
                } else {
                    machinesOnline.put(adr, 1);
                }
            }
        } catch (IOException ignored) {}
    }

    @Override
    public void interrupt() {
        super.interrupt();
        machinesChecker.interrupt();
    }
}
