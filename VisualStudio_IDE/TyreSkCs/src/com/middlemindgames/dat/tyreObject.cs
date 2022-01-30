
namespace com.middlemindgames.dat {
    /*
     * tyreObject.java
     * Victor G. Brusca 01/16/2022
     * Created on June 1, 2005, 10:57 PM by Middlemind Games
     */
    public class tyreObject {
        public int type = 0;
        public int imageIndex = -1;
        public int linkTo = -1;
        public int npcIndex = -1;
        public int charIndex = -1;
        public int posX = 0;
        public int posY = 0;
        public int searchItem = -1;
        public int stringIndex = -1;
        public int enemyIndex = -1;
        public int conversationIndex = -1;
        public int flagIndex = -1;
        public int height = 0;
        public int width = 0;
        public int angle = 0;
        public int destPosX = -1;
        public int destPosY = -1;
        public int objectSetsIndex = -1;
        public int battleIndex = -1;
        public int flagDir = 1;
        public int destDir = 1;
        public int vertOffset = -1;

        public tyreObject(int id, int ii, int lt, int NpcIndex, int px, int py, int si, int ni, int CharIndex, int Height, int Width, int ConversationIndex, int FlagIndex, int Angle, int FlagDir, int VertOffset) {
            type = id;
            imageIndex = ii;
            linkTo = lt;
            npcIndex = NpcIndex;
            posX = px;
            posY = py;
            searchItem = si;
            stringIndex = ni;
            charIndex = CharIndex;
            height = Height;
            width = Width;
            conversationIndex = ConversationIndex;
            flagIndex = FlagIndex;
            angle = Angle;
            flagDir = FlagDir;
            vertOffset = VertOffset;
        }

        public tyreObject(int Type, int ImageIndex, int LinkTo, int PosX, int PosY, int Height, int Width, int DestPosX, int DestPosY, int FlagIndex, int FlagDir, int DestDir, int VertOffset) {
            type = Type;
            imageIndex = ImageIndex;
            linkTo = LinkTo;
            posX = PosX;
            posY = PosY;
            height = Height;
            width = Width;
            destPosX = DestPosX;
            destPosY = DestPosY;
            flagIndex = FlagIndex;
            flagDir = FlagDir;
            destDir = DestDir;
            vertOffset = VertOffset;
        }

        public tyreObject(int Type, int ImageIndex, int PosX, int PosY, int Height, int Width, int VertOffset) {
            type = Type;
            imageIndex = ImageIndex;
            posX = PosX;
            posY = PosY;
            height = Height;
            width = Width;
            vertOffset = VertOffset;
        }

        public tyreObject(int Type, int ImageIndex, int PosX, int PosY, int Height, int Width, int BattleIndex, int FlagIndex, int FlagDir, int VertOffset) {
            type = Type;
            imageIndex = ImageIndex;
            posX = PosX;
            posY = PosY;
            height = Height;
            width = Width;
            battleIndex = BattleIndex;
            flagIndex = FlagIndex;
            flagDir = FlagDir;
            vertOffset = VertOffset;
        }

        public tyreObject(int Type) {
            type = Type;
        }

        public tyreObject() {
        }
    }
}
