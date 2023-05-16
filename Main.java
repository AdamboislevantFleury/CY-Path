public class Main {
  public static void main(String[] args) {
    new MaFenetre();
    new MaFenetre(); // et deux si on veut
    System.out.println(Thread.currentThread() + " en fin de main()");
  }
}
