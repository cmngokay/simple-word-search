/*GÖKAY ÇİMEN 2015123061*/

package gokaycimen_muhendislikprojesi1;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;



public class MuhendislikProjesi1 {
    String kelime;    
    int N=211; //dizi boyutu
    String[] hashkelimedizi = new String[N];//hashdizi[]'sinin string hali
    int[] hashdizi = new int[N];//hash tablosu mantığıyla oluşturulan dizi
    String[] kelimedizi = new String[N];//dosya okumada kullanılan dizi
    public MuhendislikProjesi1() { 
//Yapıcı metot ile hashdizi[]'yi sıfırlayan,metin dosyasından kelimeleri okuyan metot ve hashdizi[]'yi ekrana yazdıran metot tetikleniyor.
        sifirla();
        kelimeoku("kelimeler.txt");
        ekrandagoster();       
    }
    
    public void sifirla(){
//Herhangi bir null sorunu ile karşılaşmamak ve dizilerin kontrollerini daha etkin yapabilmek için
// hashdizi[] ve hashkelimedizi[]'sinin tüm elemanları sıfırlanıyor.
 
        for(int i=0;i<hashdizi.length;i++){
            hashdizi[i]=0;
            hashkelimedizi[i]="0";
        }
            
    }
    
    public void kelimeoku(String adres){
 //Metin dosyasından kelimeler satır satır okunuyor.
 //Çekilen her kelime , kelimedizi[] dizisine küçük harfe dönüştürülerek atılıyor.
 //Her kelime için hashdegerihesabi yapılıp , hash() metodu ile istenilen hashdizi[] oluşturuluyor.      
           try{      
               
               BufferedReader br =null;
               try{
                   br= new BufferedReader(new FileReader(adres));
               }
               catch(IOException ae){
                   ae.printStackTrace();//Okumada olabilecek hatanın detaylı bir şekilde konsola yazılmasını sağlar.
               };               
               
               int i=0;
               while((kelime=br.readLine())!=null){                 
                   kelimedizi[i]=kelime.toLowerCase();
                   int key=hashdegerihesabi(kelimedizi[i]);
                   hash(key,N,kelimedizi[i]);
                   i++;
               }
               br.close();
               
           }
           catch(IOException ex){           
               Logger.getLogger(MuhendislikProjesi1.class.getName()).log(Level.SEVERE,"hata", ex);
           }           
       
         }
    
     public int hashdegerihesabi(String kelime){
//Metoda gelen her kelimenin karakterleri tek tek alınıp,kelimedeki sırasının dördüncü kuvveti ile çarpılıp,toplanıyor.
//Elde edilen hash değeri toplam değişkeni ile geri döndürülüyor.
        int toplam=0;
        
        for(int i= 0;i<kelime.length();i++){
            
        char karakter = kelime.charAt(i);
        toplam+=((int)karakter*(Math.pow(i+1,4)));
         
            
            }
           
        return toplam;
        
        }
     
       public void hash(int key,int N,String kelime){
 //Metoda gelen hash değeri(key),dizi boyutu(N) ile indis hesabı yapılıyor(indis=key%N).
 //Hash Tablosu mantığına uygun şekilde hashdizi[]'ye yerleştiriliyor.
 //indis değerinin çakışmaması (indis+(i*i))%N ile sağlanıyor.
  
            int indis,i=0;
            
            indis=key%N;    
            
            while(hashdizi[indis] != 0){
                   
                        indis=(indis+(i*i))%N;                     
                        
                        i++;
                   
                }
            hashdizi[indis]=key;
            hashkelimedizi[indis]=kelime;        
        }
       
        public void kelimeara(String kelime){
 //Kullanıcının girdiği kelime hashdegerihesabi() metoduna gönderiliyor.Hash değeri(aranan) hesaplanıyor.Ve hashdizi[]'de aranıyor.
 //Aranan kelime bulunursa bildiriliyor,aksi halde eksiltara() ve yerdegistirara() metotları tetikleniyor.
           boolean deger=false;
           int aranan = hashdegerihesabi(kelime);
            for (int i = 0; i <hashdizi.length; i++) {
                if(hashdizi[i] == aranan){
                    deger=true;
                }              
            }
            
            if(deger == true)
               System.out.println("Girdiğiniz "+kelime+"["+hashdegerihesabi(kelime)+"]"+" kelimesi metin dosyasında bulunmaktadır!");
            else{
               System.out.println("Girdiğiniz "+kelime+" kelimesi metin dosyasında bulunmamaktadır!");
               eksiltara(kelime);
               yerdegistirara(kelime);
            }
                   
            
        }
        
        public void eksiltara(String kelime){
//Metoda gelen kelimenin harfleri tek tek eksiltilerek arama işlemi gerçekleştiriliyor.
//Döngü içerisinde ki 'i' ve 'j' değişkenleri kelime boyutu kadar dönüyor.
//'i' ve 'j' değişkelerinin farklı olduğu durumlardaki karakterler okunup term[] dizisine atılıyor.
//term[] karakter dizisi Stringe çevrilip hashdegerihesabi() metoduna gönderiliyor ve hash değeri(aranan) hesaplanıyor.
//Elde edilen hash değeri(aranan) hashdizi[]'si içerisinde aranıyor.Ve gerekli mesaj veriliyor.       
            int aranan;
            char karakter;
            char[] term = new char[kelime.length()-1];
            
            int a=0;
            int j=0;           
            
            for(int i=0;i<kelime.length();i++){
                for(j=0;j<kelime.length();j++){
                    if(j != i){
                        karakter = kelime.charAt(j);
                        term[a]=karakter;                       
                        a++;                        
                    }  
                    
                }
                
                String aranankelime = new String(term);
                aranan=hashdegerihesabi(aranankelime);
                
                for(int l=0;l<hashdizi.length;l++){
                    if(aranan == hashdizi[l]){
                        System.out.println("Girdiğiniz "+kelime+"["+hashdegerihesabi(kelime)+"]"+" kelimesi metin dosyasında "+aranankelime+"["+aranan+"]"+" olarak bulunmustur!");
                        break;
                    }                    
                }
                a=0;        
                 
            }           
                        
        }        
        
        public void yerdegistirara(String kelime){
//Metoda gelen kelimenin yanyana olan karakterlerinin yerleri değiştirilip arama işlemi gerçekleştiriliyor.
//Döngü içerisindeki 'i' değişkeni ile i==j şartında i <--> i+1 değişimi gerçekleştiriliyor.
//Değişim yapılan kelimede i ve i+1 indisleri karakterlere dahil edilmiyor.
//term[] karakter dizisi Stringe çevrilip hashdegerihesabi() metoduna gönderiliyor ve hash değeri(aranan) hesaplanıyor.
//Bulunun hash değeri(aranan) ile hashdizi[] içerisinde arama işlemi yapılıyor.Ve gerekli mesaj veriliyor.
           char karakter;
           char[] term=new char[kelime.length()];
            int aranan;                     
           
            for(int i=0;i<kelime.length()-1;i++){
                for(int j=0;j<kelime.length();j++){
                    if(i == j){
                        karakter=kelime.charAt(i+1);
                        term[i]=karakter;                        
                        karakter=kelime.charAt(i);
                        term[i+1]=karakter;                       
                       
                    }
                    if( j !=i && j != i+1){
                        karakter=kelime.charAt(j);
                        term[j]=karakter;                      
                    }              
                    
               }
                String aranankelime = new String(term);
               aranan = hashdegerihesabi(aranankelime);
                
                for(int l=0;l<hashdizi.length;l++){
                    if(aranan == hashdizi[l]){
                        System.out.println("Girdiğiniz "+kelime+"["+hashdegerihesabi(kelime)+"]"+" kelimesi metin dosyasında "+aranankelime+"["+aranan+"]"+" olarak bulunmustur!");
                        break;
                    }                    
                }    
                
            }          
            
        }
        
        public void ekrandagoster(){
 //Oluşturulan hashdizi[]'sinin string karşılığı olan hashkelimedizi[] ile kelimeler ekranda gösteriliyor.           
            System.out.println("-Hash Tablosu-");
            
            for (int i = 0; i < hashkelimedizi.length; i++) {
            if(hashkelimedizi[i] != "0"){
                System.out.println(i+")"+hashkelimedizi[i]+"["+hashdizi[i]+"]");
            }
          }
        }
        
        
    public static void main(String[] args) {
       
//nesnemizi oluşturup yapıcı metodu tetikleniyor ve okuma,diziye yerleştirme,ekrana yazma metotlarının tamamı çalıştırılıyor.
        MuhendislikProjesi1 a = new MuhendislikProjesi1();
 
 //veri girişi için gerekli arama ve secim nesnelerini oluşturuyoruz.
        Scanner arama = new Scanner(System.in);
        Scanner secim = new Scanner(System.in);
        int islem;
        String aranankelime;
        
        
//kullanıcının istediği sayıda kelime arama işlemi yapabilmesi için do-while içerisinde continue deyimi kullanıyoruz.        
        do{             
             System.out.println("---İŞLEM SEÇİNİZ---");
             System.out.println("1)Kelime Arama");
             System.out.print("2)Çıkış");
             islem=secim.nextInt();          
             
        switch(islem){
            case 1:      
               
                    System.out.println("Kelime Arama İşlemi :");
                    System.out.print("Aramak istediğiniz kelimeyi giriniz :");
                    aranankelime=arama.nextLine();
                    a.kelimeara(aranankelime);
                    continue;
                                    
            case 2:
                   System.out.println("Program Sonlandırıldı !");                
                   break;
                
            default:
                   System.out.println("Dikkat : 1 veya 2 değerlerinden birini giriniz !");
                   continue;
        }
            break;
        }while(true);

    }
    
}
