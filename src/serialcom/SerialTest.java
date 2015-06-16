/*
Esta Class foi baseada na Class SerialTest disponibilizada por arduino.cc

*/


package serialcom;

import equitrack1.pkg.FXMLDocumentController;
import static equitrack1.pkg.FXMLDocumentController.data; 
import gnu.io.CommPortIdentifier; 
import gnu.io.SerialPort; 
import gnu.io.SerialPortEvent; 
import gnu.io.SerialPortEventListener; 
import java.io.BufferedReader; 
import java.io.IOException; 
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Enumeration;


public class SerialTest implements SerialPortEventListener {
	SerialPort serialPort;
        
	private BufferedReader input;
	/** The output stream to the port */
	public static OutputStream output;
	/** Milliseconds to block while waiting for port open */
	private static final int TIME_OUT = 2000;
	/** Default bits per second for COM port. */
	private static final int DATA_RATE = 9600;
        //Data
        
        public int i = 0;
        private FXMLDocumentController docCont;
        private static long zeroAdjust = 0;
        private long[] calibrationArray = new long[20];
        private long sum = 0;
        
        
        //Este metodo inicia a comunicacao serie
	public void initialize(FXMLDocumentController var) {
                
                docCont = var;
		CommPortIdentifier portId = null;
		Enumeration portEnum = CommPortIdentifier.getPortIdentifiers();
                String PORT_NAMES[] = {
                                            "/dev/tty.usbserial-A9007UX1", // Mac OS X
                                            "/dev/ttyACM0", // Raspberry Pi
                                            "/dev/ttyUSB0", // Linux
                                            
                                            docCont.serialComSetup.getText()// Windows
                                            };
		//First, Find an instance of serial port as set in PORT_NAMES.
		while (portEnum.hasMoreElements()) {
			CommPortIdentifier currPortId = (CommPortIdentifier) portEnum.nextElement();
			for (String portName : PORT_NAMES) {
				if (currPortId.getName().equals(portName)) {
					portId = currPortId;
					break;
				}
			}
		}
		if (portId == null) {
			System.out.println("Could not find COM port.");
			return;
		}
                
		try {
			// open serial port, and use class name for the appName.
			serialPort = (SerialPort) portId.open(this.getClass().getName(),
					TIME_OUT);

			// set port parameters
			serialPort.setSerialPortParams(DATA_RATE,
					SerialPort.DATABITS_8,
					SerialPort.STOPBITS_1,
					SerialPort.PARITY_NONE);

			// open the streams
			input = new BufferedReader(new InputStreamReader(serialPort.getInputStream()));
			output = serialPort.getOutputStream();

			// add event listeners
			serialPort.addEventListener(this);
			serialPort.notifyOnDataAvailable(true);
		} catch (Exception e) {
			System.err.println(e.toString());
		}
	}

	/**
	 * This should be called when you stop using the port.
	 * This will prevent port locking on platforms like Linux.
	 */
	public synchronized void close() {
		if (serialPort != null) {
			serialPort.removeEventListener();
			serialPort.close();
		}
	}

	/**
	 * Handle an event on the serial port. Read the data and print it.
	 */
        /*Sempre que o arduino envia os dados este metodo le e passa-os para a variavel data
         *Esta variavel vai ser usada no metodo updateChart()     
        */
	public synchronized void serialEvent(SerialPortEvent oEvent) {
		if (oEvent.getEventType() == SerialPortEvent.DATA_AVAILABLE) {
                        try {
                            
                            String inputLine=input.readLine();
                            
                            String code;
                            code = inputLine.substring(1, 2);
                           
                            switch (code){
                                case "F": if(docCont.tipoTeste.getValue().toString().charAt(0)== '0'){
                                            data.addForca((Long.parseLong(inputLine.substring(2), 10) - zeroAdjust)*0.00209-0.2475);
                                            //System.out.println(data.getForca((int)i));
                                            }
                                           if(docCont.tipoTeste.getValue().toString().charAt(0)== '1'){
                                            data.addForca((Long.parseLong(inputLine.substring(2), 10) - zeroAdjust)*0.00192-0.21);
                                            //System.out.println(data.getForca((int)i));
                                            }
                                          break;
                                case "D": data.addDeslocamento(Long.parseLong(inputLine.substring(2), 10)*0.011);
                                          //System.out.println(data.getDeslocamento((int)i));
                                          break;
                                case "I": System.out.println(inputLine.substring(1,4));
                                          break;
                                case "C": System.out.println(inputLine.substring(2));
                                          //calibrationArray[i] = Long.parseLong(inputLine.substring(2), 10);
                                          zeroAdjust = Long.parseLong(inputLine.substring(2), 10);
                                          System.out.println(zeroAdjust + "aqui");
                                          //i++;
                                          break;
                            }
                            docCont.updateChart();
                            //System.out.println(i);
                            /*if(i==19){
                                int length = calibrationArray.length;
                                System.out.println(length);
                                
                                for(int j = 0; j == length; j++){
                                    System.out.println(calibrationArray[j] + " CalibA");
                                    sum = sum + calibrationArray[j];
                                }
                                
                                zeroAdjust = sum/length;
                                System.out.println(length + " Array length");
                                System.out.println(sum + " sum");
                                System.out.println(zeroAdjust + "aqui");
                            } */ 
                        }
                        catch (Exception e) {
                            System.err.println(e.toString());
                       }
                       
		}
	// Ignore all the other eventTypes, but you should consider the other ones.
	}
        
        public void writetoport(String send) {

            try {
                    output.write(send.getBytes());
                    output.flush();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
        }
       
}
        
        
