public class Main {

    public static void main(String[] args) {
        GameServer server = new GameServer();
        server.init();
        server.waitForClients();

    }
}
