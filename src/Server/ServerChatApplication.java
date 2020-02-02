package Server;
    import java.io.IOException;
    import java.net.ServerSocket;
    import java.net.Socket;

public class ServerChatApplication {
    private static ServerSocket serversocket;
    private static boolean salir;
    private static int cont;
    private static Users list;

    public static void main(String[] args) {
        try{
            cont=0;
            list = new Users();
            salir=false;
            serversocket=new ServerSocket(7001);
            System.out.println("Server");
            while(!salir){
                Socket socket=serversocket.accept();
                list.addUser(cont, socket);
                ServerConnection st=new ServerConnection(socket,list,cont);
                st.start();
                cont++;
            }
        }catch(IOException io){
            System.out.println("Error en el  server");
        }
    }
}
