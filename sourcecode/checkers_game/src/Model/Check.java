package Model;
import java.io.Serializable;

public class Check implements Serializable{

	Piece checkPiece;
	private int lineNumber;
	private int colomnNumber;
	Gameboard gameboard;

        public Check(int lineNumber, int colomnNumber, Gameboard gameboard) {
            //this.checkPiece = checkPiece;
            this.lineNumber = lineNumber;
            this.colomnNumber = colomnNumber;
            this.gameboard = gameboard;
            
            Piece checkPiece = null;
            /*
            Test Code            
            *//*
            if((lineNumber==1)&&(colomnNumber==2)){
                checkPiece = new Man(this,"black",0);
                this.checkPiece=checkPiece;
            }else if((lineNumber==1)&&(colomnNumber==4)){
                checkPiece = new Man(this,"black",0);
                this.checkPiece=checkPiece;
            } else if((lineNumber==9)&&(colomnNumber==2)){
                checkPiece = new Man(this,"black",0);
                this.checkPiece=checkPiece;
            }else if((lineNumber==2)&&(colomnNumber==5)){
                checkPiece = new King(this,"black");
                this.checkPiece=checkPiece;
            }else if((lineNumber==2)&&(colomnNumber==7)){
                checkPiece = new King(this,"black");
                this.checkPiece=checkPiece;
            }else if((lineNumber==8)&&(colomnNumber==9)){
                checkPiece = new Man(this,"black",0);
                this.checkPiece=checkPiece;
            } else if((lineNumber==4)&&(colomnNumber==3)){
                checkPiece = new King(this,"black");
                this.checkPiece=checkPiece;
            }else if((lineNumber==4)&&(colomnNumber==7)){
                checkPiece = new King(this,"black");
                this.checkPiece=checkPiece;
            }else if((lineNumber==5)&&(colomnNumber==7)){
                checkPiece = new King(this,"black");
                this.checkPiece=checkPiece;
            }else if((lineNumber==6)&&(colomnNumber==1)){
                checkPiece = new King(this,"black");
                this.checkPiece=checkPiece;
            }else if((lineNumber==7)&&(colomnNumber==0)){
                checkPiece = new King(this,"white");
                this.checkPiece=checkPiece;
            } else {
                this.checkPiece=checkPiece;
            }
            *//*
            End test code
            */         
            if((lineNumber>=0)&&(lineNumber<=3)){
                if(((lineNumber%2)==0)&&((colomnNumber%2)==1)){
                    checkPiece = new Man(this,"black",0);
                    this.checkPiece=checkPiece;
                } else if(((lineNumber%2)==1)&&((colomnNumber%2)==0)){
                    checkPiece = new Man(this,"black",0);
                    this.checkPiece=checkPiece;
                } else {
                    this.checkPiece=checkPiece;
                }                
            } else if((lineNumber>=6)&&(lineNumber<=9)){
                if(((lineNumber%2)==0)&&((colomnNumber%2)==1)){
                        checkPiece = new Man(this,"white",1);
                        this.checkPiece=checkPiece;
                    } else if(((lineNumber%2)==1)&&((colomnNumber%2)==0)){
                        checkPiece = new Man(this,"white",1);
                        this.checkPiece=checkPiece;
                    } else {
                        this.checkPiece=checkPiece;
                    }
            } else {
                this.checkPiece=checkPiece;
            }
        }

        public Piece getcheckPiece() {
            return checkPiece;
        }

        public void setcheckPiece(Piece checkPiece) {
            this.checkPiece = checkPiece;
        }       
        

        public Gameboard getGameboard() {
            return gameboard;
        }      
        

	public int getLineNumber() {
		return this.lineNumber;
	}

	public void setLineNumber(int lineNumber) {
		this.lineNumber = lineNumber;
	}

	public int getColomnNumber() {
		return this.colomnNumber;
	}

	public void setColomnNumber(int colomnNumber) {
		this.colomnNumber = colomnNumber;
	}
        
        public boolean nearCheckOccupied(String color){
            boolean isPossiblyGoingToDieHere = false;
            int lineNumber = this.getLineNumber();
            int colomnNumber = this.getColomnNumber();
            Gameboard gameboard = this.getGameboard();
            Check currentCheck;
            Piece currentPiece;
            boolean closestCheck = true;
            
            while(((lineNumber-1)>=0)&&((colomnNumber-1)>=0)){
                currentCheck = gameboard.getCheckByLineColomn((lineNumber-1), (colomnNumber-1));
                currentPiece = currentCheck.getcheckPiece();
                if((currentPiece!=null)&&(closestCheck)&&(!currentPiece.getColor().equals(color))){
                   isPossiblyGoingToDieHere = true;
                   closestCheck=false;
                }else if ((currentPiece!=null)&&(currentPiece instanceof King)&&(!currentPiece.getColor().equals(color))){
                   isPossiblyGoingToDieHere = true;
                }
                lineNumber--;
                colomnNumber--;
            }
            lineNumber = this.getLineNumber();
            colomnNumber = this.getColomnNumber();
            closestCheck = true;
            while(((lineNumber-1)>=0)&&((colomnNumber+1)<gameboard.getNbColomns())){
                currentCheck = gameboard.getCheckByLineColomn((lineNumber-1), (colomnNumber+1));
                currentPiece = currentCheck.getcheckPiece();
                if((currentPiece!=null)&&(closestCheck)&&(!currentPiece.getColor().equals(color))){
                   isPossiblyGoingToDieHere = true;
                   closestCheck=false;
                }else if ((currentPiece!=null)&&(currentPiece instanceof King)&&(!currentPiece.getColor().equals(color))){
                   isPossiblyGoingToDieHere = true;
                }
                lineNumber--;
                colomnNumber++;
            }
            lineNumber = this.getLineNumber();
            colomnNumber = this.getColomnNumber();
            closestCheck = true;
            while(((lineNumber+1)<gameboard.getNbLines())&&((colomnNumber-1)>=0)){
                currentCheck = gameboard.getCheckByLineColomn((lineNumber+1), (colomnNumber-1));
                currentPiece = currentCheck.getcheckPiece();
                if((currentPiece!=null)&&(closestCheck)&&(!currentPiece.getColor().equals(color))){
                   isPossiblyGoingToDieHere = true;
                   closestCheck=false;
                }else if ((currentPiece!=null)&&(currentPiece instanceof King)&&(!currentPiece.getColor().equals(color))){
                   isPossiblyGoingToDieHere = true;
                }
                lineNumber++;
                colomnNumber--;
            }
            lineNumber = this.getLineNumber();
            colomnNumber = this.getColomnNumber();
            closestCheck = true;
            while(((lineNumber+1)<gameboard.getNbLines())&&((colomnNumber+1)<gameboard.getNbColomns())){
                currentCheck = gameboard.getCheckByLineColomn((lineNumber+1), (colomnNumber+1));
                currentPiece = currentCheck.getcheckPiece();
                if((currentPiece!=null)&&(closestCheck)&&(!currentPiece.getColor().equals(color))){
                   isPossiblyGoingToDieHere = true;
                   closestCheck=false;
                }else if ((currentPiece!=null)&&(currentPiece instanceof King)&&(!currentPiece.getColor().equals(color))){
                   isPossiblyGoingToDieHere = true;
                }
                lineNumber++;
                colomnNumber++;
            }            
            return isPossiblyGoingToDieHere;
        }

	public boolean isOccupied() {
		if(this.getcheckPiece()==null){
                    return false;
                } else {
                    return true;
                }                
	}

}