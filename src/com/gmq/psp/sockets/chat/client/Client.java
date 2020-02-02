package com.gmq.psp.sockets.chat.client;
    import java.io.DataInputStream;
    import java.io.IOException;
    import java.net.Socket;

public class Client extends Thread{
    private Socket socket;
    private DataInputStream in;
    private boolean end;
    private String mensaje;
    private static String nombre;

    public Client(Socket socket,String nombre){
        this.socket=socket;
        this.nombre=nombre;
    }

    public void run(){
        try{
            end=false;
            in=new DataInputStream(socket.getInputStream());
            while(!end){
                mensaje=in.readUTF();
                System.out.println(mensaje);
                if(mensaje.equalsIgnoreCase("- "+nombre+" DESCONECTADO")){
                    end=true;
                }
            }
            //CERRAMOS EL SOCKET
            socket.close();
            //MENSAJE DE ERROR
        }catch(IOException io){
            System.out.println("Error client");
            io.printStackTrace();
            end=true;
        }
    }
}
