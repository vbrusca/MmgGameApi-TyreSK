
namespace com.middlemindgames.dat {
    /*
     * linkTable.java
     * Victor G. Brusca 01/16/2022
     * Created on June 1, 2005, 10:57 PM by Middlemind Games
     */
    public class linkTable {
        public int roomIndex;
        public int linkTop;
        public int linkLeft;
        public int linkBottom;
        public int linkRight;
        public int frequency;
        public bool hasEnemies;
        public int flagIndex;
        public int stringIndex;
        public int objectSetIndex;
        public int enemyLevel;

        public linkTable() {
        }

        public void setFrequency(int fr) {
            frequency = fr;
            if (frequency > 0) {
                hasEnemies = true;
            } else {
                hasEnemies = false;
            }
        }

        public void setEnemyLevel(int el) {
            if (el != 255 && el != 0) {
                enemyLevel = el;
            } else {
                enemyLevel = 1;
            }
        }
    }
}
