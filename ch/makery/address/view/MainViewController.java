package ch.makery.address.view;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.StackedAreaChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import ch.makery.address.MainApp;

public class MainViewController {
	@FXML
	TextField media;
	@FXML
	TextField deviazione;
	@FXML
	ChoiceBox<?> portafoglio;
	
	@FXML
	ImageView refresh;
	
	@FXML
	NumberAxis xAxis;
	@FXML
	NumberAxis yAxis;
	
    @FXML
    StackedAreaChart<Number, Number> grafico;
    
    double ScaleXinf;
	double ScaleXsup;
	double Delta;
	double Dev;
	int ZoomIn;
	int ZoomOut;
	int ZoomTok;

    
    final double e = 2.7182818284590452353602874713526624977572470936999595749; //numero di Keplero e
    final double pi = 3.1415926535897932384626433832795028841971693993751058209; //pi greco
    
    MainApp mainApp;

    /**
     * Costruttore
     * chiamato prima del metodo initialize().
     */
    public MainViewController() {
    }

    /**
     * inizializzo la classe di controllo
     * dopo che il file FXML è stato aperto.
     */
    @FXML
    private void initialize() {
    	media.setText("0");
    	deviazione.setText("1");
    	xAxis.setAutoRanging(false);
    	
        //yAxis.setAutoRanging(false);
        //yAxis.setLowerBound(0);
    	//yAxis.setUpperBound(0.5);
        //yAxis.setTickUnit(0.1);
    	
    	grafico.setOnScroll(new EventHandler<ScrollEvent>() {
    	    public void handle(ScrollEvent event) {
    	        event.consume();
    	        
    	        

    	        if (event.getDeltaY() == 0) {
    	            return;
    	        }

    	        else if (event.getDeltaY() < 0) {
    	        	
        	        if (!((ScaleXsup-(Dev/5)) <= Double.parseDouble(media.getText())))
        	        {
        	        	//aggiorno variabili coordinate
        	        	ScaleXinf = (ScaleXinf+Delta);
            	    	ScaleXsup = (ScaleXsup-Delta);
            	    	
            	    	//aggiorno il grafico
        	        	xAxis.setLowerBound(ScaleXinf);
            	    	xAxis.setUpperBound(ScaleXsup);
            	        xAxis.setTickUnit((ScaleXsup-ScaleXinf)/10);
        	        
        	        }
        	        else 
        	        {
        	        	//System.out.println("ZOOM IN MASSIMO");
        	        }
        	        
    	            return;
    	        }
    	        
    	        else if (event.getDeltaY() > 0) {
    	        	
        	        if ((Double.parseDouble(media.getText())+Dev) >= ScaleXsup)
        	        {
        	        	//aggiorno variabili coordinate
        	        	ScaleXinf = (ScaleXinf-Delta);
            	    	ScaleXsup = (ScaleXsup+Delta);
            	    	
            	    	/* DEBUG
            	    	System.out.print("media+Dev:	");
            	    	System.out.println((Double.parseDouble(media.getText())+Dev));
            	    	System.out.print("ScaleXsup:	");
            	    	System.out.println(ScaleXsup);
            	    	*/
            	    	
            	    	//aggiorno il grafico
        	        	xAxis.setLowerBound(ScaleXinf);
            	    	xAxis.setUpperBound(ScaleXsup);
            	        xAxis.setTickUnit((ScaleXsup-ScaleXinf)/10);
        	        
        	        }
        	        else 
        	        {
        	        	//System.out.println("ZOOM OUT MASSIMO");
        	        }
        	        
    	            return;
    	        }
    	        
    	        
    	    	
    	    	
    	    }
    	});
    	
    	

    }
    
    /**
     * mi serve per aggiornare il grafico
     * dopo che inserisco i valori di media e deviazione standard
     */
    @FXML
    private void refresh() {
    	yAxis.setAutoRanging(false);
    	
    	System.out.println(grafico.getData()); 
    	grafico.getData().remove(0);
    	
    	System.out.println(grafico.getData());
    	grafico.getData().add(getDistribuzione(new XYChart.Series<>(), Double.parseDouble(media.getText()), Double.parseDouble(deviazione.getText())));
    	
    	System.out.println(grafico.getData());

    }

    /**
     *
     * 
     * @param mainApp
     */
    
    public void setMainApp(MainApp mainApp) {
        
        grafico.getData().add(getDistribuzione(new XYChart.Series<>(), Double.parseDouble(media.getText()), Double.parseDouble(deviazione.getText())));
        grafico.setLegendVisible(false);
        grafico.setVerticalZeroLineVisible(false);
        
        
        
    }
    
    /**
     * Calcolo del Grafico
     * 
     * @author Alberto
     */
    public XYChart.Series<Number, Number> getDistribuzione(XYChart.Series<Number, Number> ListData, double media, double deviazione){
    	double y;
		double x;
		double yRes = 0.5;
		double yResTemp = 0;
		
		
		Dev = Math.abs(deviazione)*5;
		ScaleXinf = media-Dev;
		ScaleXsup = media+Dev;
		
		/* DEBUG
		System.out.println(ScaleXinf);
    	System.out.println(ScaleXsup);
    	*/
		
    	for(double i = ScaleXinf;i<ScaleXsup;i=i+(deviazione*0.1))
    	{
    		x = i;
    		
    		y = ((1)/(deviazione*(Math.sqrt(2*pi))))*(Math.pow(e, (-1*(Math.pow((x-media), 2)/(Math.pow((2*deviazione), 2))))));
    		
    		ListData.getData().add(new XYChart.Data<Number, Number>(x, y));
    		
    		if (i == (media+Dev))
    		{
    			//System.out.println(y);
    		}
    		else
    		{
    			//System.out.println(y);
    			if (yResTemp < y)
    			{
    				yResTemp = y;
    				yRes = y+(y/10);
    			}
    			else
    			{	
    			}
    			
    		}
    		
    	}
    	
    	//Setto il grafico nelle ascisse
    	xAxis.setLowerBound(ScaleXinf);
    	xAxis.setUpperBound(ScaleXsup);
        xAxis.setTickUnit((ScaleXsup-ScaleXinf)/10);
        
       //Setto il grafico nelle ordinate
    	yAxis.setUpperBound(yRes);
        yAxis.setTickUnit(yRes/10);
        
        
        //Creo il Delta per lo zoom
        Delta = Math.abs(deviazione*0.3);
        //System.out.println(Delta);
    	
        //Do il nome all'array di valori
    	ListData.setName("Distribution");
    	
    	return ListData;
    }
    
    


}
