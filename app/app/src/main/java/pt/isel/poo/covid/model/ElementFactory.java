package pt.isel.poo.covid.model;
public class ElementFactory {

    public LevelElement getElement(int c,int l,char type,Level level){
        switch (type) {
            case '@':

                return new Hero(new Location(c,l),level);

            case 'X':
                return new Wall(new Location(c,l));


            case '*':

                return new Virus(new Location(c,l),level);

            case 'V':
                return new TrashCan(new Location(c,l));

            default:
                return null;

        }
    }
}
