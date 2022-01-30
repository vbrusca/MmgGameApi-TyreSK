
namespace com.middlemindgames.dat {
    /*
     * conversation.java
     * Victor G. Brusca 01/16/2022
     * Created on February 14, 2006, 6:46 PM By Middlemind Games
     */
    public class conversation {
        public int stringIdNpc = -1;
        public int searchIndex = -1;
        public bool selfTriggered = false;
        public int flagIndex = -1;
        public int flagIndex1 = -1;
        public int conversationIndex = -1;
        public bool takeItem = true;
        public int battlesIndex;

        public conversation() {
        }

        public void setSelfTriggered(int val) {
            if (val == 1) {
                selfTriggered = true;
            } else {
                selfTriggered = false;
            }
        }

        public void setTakeItem(int val) {
            if (val == 1) {
                takeItem = true;
            } else {
                takeItem = false;
            }
        }
    }
}