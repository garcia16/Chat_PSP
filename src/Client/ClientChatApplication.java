package Client;

    import java.io.BufferedReader;
    import java.io.DataOutputStream;
    import java.io.IOException;
    import java.io.InputStreamReader;
    import java.net.Socket;

public class ClientChatApplication {

    private static DataOutputStream ou;
    private static BufferedReader in;
    private static Socket socket;
    private static String mensaje;
    private static String nombre;
    private static boolean end;
    private static Client cl;

    public static void main(String[] args){
        initialize();
        try{
            if (args.length == 0) {
                System.out.println("Introduce el nombre");
            }else {
                nombre=args[0];
                cl=new Client(socket,nombre);
                cl.start();
                ou.writeUTF(nombre);
                String  first = "Gracias, soy "+ nombre;
                ou.writeUTF(first);
            }

        }catch(IOException io1){
            System.out.println("Error input text");
        }
        while(!end){
            try{
                mensaje=in.readLine();
                ou.writeUTF(mensaje);
                if(mensaje.equalsIgnoreCase("salir")){
                    end=true;
                }
            }catch(IOException io2){
                System.out.println("Error");
            }
        }
    }

    private static void initialize(){
        try{
            socket=new Socket("localhost",7001);
            ou=new DataOutputStream(socket.getOutputStream());
            in=new BufferedReader(new InputStreamReader(System.in));
            end=false;
        }catch(IOException io){
            io.printStackTrace();
        }
    }
}
