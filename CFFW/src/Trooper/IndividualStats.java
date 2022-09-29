package Trooper;

import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;
public class IndividualStats implements Serializable {

   public String name; 
   public int P1; 
   public int P2; 
   public int pistolRWS; 
   public int rifleRWS; 
   public int launcherRWS; 
   public int heavyRWS;
   public int subgunRWS;  

   public IndividualStats(int combatActions, int sal, int pistol, int rifle, int launcher, int heavy, int subgun, boolean number) {
      int actionPoints = 0;
      
      if(number) {
    	  this.name = '"' + generateNames() + '"' + " " + generateNumber();
      } else {
    	  this.name = generateNames();
      }
      
      if(combatActions <= 1) {
    	  actionPoints = 2;
      } else if(combatActions <= 2) {
    	  actionPoints = 2;
      } else if(combatActions <= 3) {
    	  actionPoints = 3;
      } else if(combatActions <= 4) {
    	  actionPoints = 4;
      } else if(combatActions <= 5) {
    	  actionPoints = 4;
      } else if(combatActions <= 6) {
    	  actionPoints = 4;
      } else if(combatActions <= 7) {
    	  actionPoints = 5;
      } else if(combatActions <= 8) {
    	  actionPoints = 6;
      } else if(combatActions <= 9) {
    	  actionPoints = 8;
      } else if(combatActions <= 10) {
    	  actionPoints = 10;
      } else {
    	  actionPoints = 10; 
      }
      
      if(actionPoints % 2 == 0) {
         this.P1 = actionPoints / 2; 
         this.P2 = actionPoints / 2; 
      } else {
         this.P1 = actionPoints / 2; 
         this.P2 = actionPoints / 2 + 1; 
      }
      this.pistolRWS = pistol;     
      this.rifleRWS = rifle; 
      this.launcherRWS = launcher; 
      this.heavyRWS = heavy; 
      this.subgunRWS = subgun; 
      
   }
   
   // Returns a name from an array of names 
   public static String generateNames(){
      String[] array = {"Deuce", "Bruiser", "Maine", "Lip", "Ilip", "Craze", "Ace", "Sparrow", "Wing", "Jumper", "Blast", "Evo", "Hunter", "Reaper", "Ghost", "Sigma", "Omega", "Bravo", "Six", "Alpha", "Base", "Lucky", "Fi", "Darman", "Niner", "Maze", "Fore", "Three", "Two", "Fives", "Echo", "Gutter", "Cutter", "Cut up", "Sync", "Drake", "Drako", "Chopper", "Flick", "Click", "Quick", "Bolt", "Dash", "Wovod", "Gets", "Minnand", "Krok", "Churt", "Kejutt", "Puk", "Cryg", "Kigg", "Crag", "Zog", "Eeruz", "Roc", "Uges", "Rott", "Fuhl", "Qysol", "Thorn", "Prick", "Kix", "Appo", "Fox", "Eel", "Maverick", "Prime", "Champ", "Arrow", "Banks", "Baris", "Barr", "Bel", "Bellows", "Blitz", "Blunt", "Blasty", "Blackout", "Bow", "Bouncer", "Boost", "Bry", "Brolis", "Burner", "Cale", "Cameron", "Can", "Cannon", "Chain", "Charger", "Chatter", "Droid Bait", "Colt", "Commet", "Contrail", "Cooker", "Coric", "Cob", "Cov", "Cor", "Crest", "Crasher", "Crosshair", "Crys", "Cut", "Den", "Del", "Cell", "Davijann", "Typhe", "Derel", "Dev", "Deviss", "Digger", "Di'kut", "Ding", "Dogma", "Doom", "Dom", "Dov", "Dox", "Draa", "Drayk", "Dropkick", "Kick", "Dyre", "Echo", "Edge", "Engle", "Ennen", "Evader", "Ball", "Faie", "Falco", "Fil", "Fire", "Flak", "Flash", "Flanker", "Demon", "Flashpoint", "Point", "Flyby", "Forr", "Forry", "Fox", "Frey", "Frost", "Fury", "Fyn", "Gaffa", "Galle", "Gamma", "Ganch", "Maine", "Gear", "Gearshift", "Gears", "Ged", "Gett", "Gree", "Green", "Grey", "Gunner", "Gus", "Hamm", "Hammer", "Hard", "Hardcase", "Havoc", "Hawk", "Herc", "Heavy", "Hez", "Hil", "Hunter", "Headshot", "Deadshot", "Deathstroke", "Horns", "Hotshot", "Inc", "Ince", "Jag", "Ion", "Jag", "Jangotat", "Jark", "Jax", "Jay", "Jayk", "Jek", "Jenks", "Jesse", "Jenkons", "Jester", "Jet", "Jez", "Jind", "Jink", "Jinkx", "Jind", "Joc", "Joker", "Jorir", "Justice", "Kaddak", "Kagi", "Kano", "Keeli", "Kappa", "Kaylon", "Kef", "Keller", "Kickback", "Kicker", "Killer", "King", "Kite", "Kix", "Klick", "Knuckles", "Koho", "Korbel", "Kosmos", "Kupe", "Lassar", "Law", "Level", "Lex", "Lio", "Lock", "Longshot", "Prophet", "Lucky", "Lunn", "Mack", "Mag", "Mapper", "Marrt", "Mar'ek", "Matchstick", "Menace", "Mixer", "Mixx", "Mojo", "Monnk", "Mort", "Mowgli", "Mortar", "Moz", "Mu", "Muzzle", "Nax", "Neyo", "Nilo", "Nu", "Nub", "Oake", "Olun", "Ox", "Oz", "Parsec", "Patch", "Patches", "Peel", "Pi", "Ponds", "Pulsar", "Psi", "Quo", "Racket", "Rafe", "Rain", "Ram", "Ras", "Rattl", "Razor", "Recon", "Red", "Rede", "Reed", "Remo", "Remy", "Ridge", "Ringo", "Rod", "Scope", "Ross", "Ronto", "Rys", "Salvo", "Sandcat", "Scorch", "Scope", "Scaps", "Scythe", "Seefor", "Seeker", "Shawdow", "Sharp", "Shiv", "Shock", "Shooter", "Silver", "Slick", "Sirty", "Skiffer", "Sketch", "Slammer", "Space", "Spade", "Spanner", "Spar", "Spark", "Spitter", "Splice", "Squawk", "Star", "Stec", "Sten", "Stinge", "Stinger", "Switch", "Stone", "Striker", "Stripe", "Styles", "Switch", "Swoop", "Syke", "Tacks", "Tag", "Taler", "Tam", "Ram", "Tarvyn", "Larek", "Lark", "Tau", "Tel", "Tavo", "Tech", "Tenn", "Thill", "Throttle", "Trap", "Trapper", "Trigger", "Troy", "Tucker", "Tup", "Twelve", "Tyto", "Six", "Seven", "Eight", "Four", "Zero", "Turbo", "Epslion", "Vaize", "Valiant", "Var", "Vargus", "Vere", "Vill", "Vin", "Voca", "Warthog", "Wave", "Waxer", "Whiplash", "Wildfre", "Wingnut", "Wooley", "Wrecker", "Xoni", "X", "Yover", "Zag", "Zak", "Zeer", "Zeke", "Zeta", "Adade", "Ade", "A'den", "Adenn", "Ad'eta", "Alorir", "Aranar", "Arr", "Beskad", "Bev", "Bevik", "Burk'yc", "Cabur", "Protector", "Guardian", "Cuyan", "Ca'tra", "Orar", "Galaar", "Parjai", "Fenn", "Wad'e", "Goran", "Mij", "Kad", "Munin", "Tor", "Pre", "Lorka", "Teti", "Fenri", "Dred", "Makin", "Rako", "Deth", "Rohlan", "Cham", "Gar", "Bossan", "Kote", "Epo", "Goran", "Esok", "Carr", "Hokan", "Ghes", "Lgi", "Myles", "Klin", "Nif", "Talgal", "Vhonte", "Novoc", "Vau", "Viba", "Gustav", "Ward", "Jigger"};
      int rnd = new Random().nextInt(array.length);
      return array[rnd];
   }
   
   // Generates a CT number 
   // Returns a string formatted as follows: 
   // CT-##-####
   public static String generateNumber(){
      String rslt = "CT-";
      Random rand = new Random();
      for(int i = 0; i < 2; i++) {
         rslt = rslt + rand.nextInt(10);  
      }
      rslt = rslt + "-";
      for(int i = 0; i < 4; i++){
         rslt = rslt + rand.nextInt(10);  
      }
      return rslt; 
   }
}