package veintiuno;

public class Dado {      
  // Variables est�ticas.  
  private final static int CARAS_DADO = 6;  
  
  // Variables   
  private int cara;  
  
  // Constructor   
  public Dado() {     
    this.tirarDado();   
  }  
  
  // Getter   
  public int getCara() {     
    return this.cara;   
  }      
  
  // M�todos    
  public void tirarDado() {     
    this.cara = 1 + (int)(Math.random() * CARAS_DADO);
  }

}