package Server;
    import java.io.DataInputStream;
    import java.io.DataOutputStream;
    import java.io.IOException;
    import java.net.Socket;
    import java.util.Iterator;
    import java.util.Map;

public class ServerConnection extends Thread{
    private String mensaje;
    private String first;
    private static String nombre;
    private Socket socket;
    private int id;
    private DataInputStream in;
    private DataOutputStream ou;
    private Users list;
    private boolean salir;

    public void run(){
        try{
            ou=new DataOutputStream(socket.getOutputStream());
            in=new DataInputStream(socket.getInputStream());
            //ASI NO PASA POR ENVIAR MENSAJE
            salir=false;
            ou.writeUTF("Bienvenido al chat");
            //RECIBE EL NOMBRE
            nombre=in.readUTF();
            first=in.readUTF();
            //SE PASA PARA QUE SE SEPA QUIEN ESTÁ CONECTADO
            enviarMensaje(first);
            //IMPRIMIR MENSAJE
            System.out.println(first);
        }catch(IOException io1){
            System.out.println("Error Server");
        }
        while(!salir){
            try{
                mensaje=in.readUTF();
                //SE VERÁ LO QUE SE VA A ENVIAR
                System.out.println("- "+nombre+" ha escrito: "+mensaje);
                //SI SE ESCRIBE "exit" EL MENSAJE NO SE ENVIA, Y CERRAMOS EL SOCKET DEL CLIENTE
                if(mensaje.equalsIgnoreCase("salir")){
                    salir=true;
                    enviarMensaje("- "+nombre+" DESCONECTADO");
                    removeClient();
                    //SI NO ESCRIBE EXIT, ENVIARA EL MENSAJE NORMAL
                }else{
                    enviarMensaje("- "+nombre+" ha escrito: "+mensaje);
                }

            }catch(IOException io2){
                System.out.println("Error Server");
                io2.printStackTrace();
            }
        }
    }

    private void removeClient(){
        list.removeUser(id);
    }

    public ServerConnection(Socket socket,Users list,int id){
        this.socket=socket;
        this.id=id;
        this.list=list;
    }
    private void enviarMensaje(String mensaje){
        try{
            Iterator it=list.getUsers().entrySet().iterator();
            while(it.hasNext()){
                Map.Entry pair = (Map.Entry)it.next();
                // SI EL USUARIO MANDA EL MENSAJE NO LO RECIBIRÁ
                if(Integer.parseInt(pair.getKey().toString())!=id) {
                    ou= new DataOutputStream(((Socket) pair.getValue()).getOutputStream());
                    ou.writeUTF(mensaje);
                }
            }
        }catch(IOException io){
            System.out.println("Error al mandar el mensaje");
        }
    }
}
