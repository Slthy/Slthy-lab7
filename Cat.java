import java.lang.Math;

public class Cat extends Creature {

    // dir: 0=North, 1=East, 2=South, 3=West.
    //
    //
    //
    // N (r-1,c+0)
    // 0
    // (r+0,c-1) W 3 [ ] 1 E (r+0,c+1)
    // 2
    // S (r+1,c+0)
    //
    //
    //
    // 

    private int movesSinceMouseEaten = 1;
    private Mouse targetMouse;

    public Cat(int x, int y, City city) {
        super(x, y, city);
        this.stepLen = 2;
        this.lab = LAB_YELLOW;
    }

    public void takeAction() {
        if (dead == true) return;
        checkToEat();
        findClosestMouse();

        if (targetMouse != null) {
            dirToTarget(targetMouse);
        }
    }

    public void step() {
        killCatMaybe();
        maybeTurn();
        movesSinceMouseEaten++;
        super.step();
    }

    private boolean findClosestMouse() {
        int targetDist = Integer.MAX_VALUE;
        boolean mouseFound = false;
        for (Creature c : city.creatures) {
            if (mouseInDistance(c) != -1 && mouseInDistance(c) < targetDist && (c instanceof Mouse)) {
                setTargetMouse(c);
                targetDist = mouseInDistance(c);
                mouseFound = true;
            } else 
                mouseFound = false;
        }
        if (mouseFound) {
            return true;
        } else {
            setTargetMouse(null);
            return false;
        }
    }

    private int mouseInDistance(Creature c) {
        if (this.getGridPoint().dist(c.getGridPoint()) < 20 && (c instanceof Mouse)) {
            return this.getGridPoint().dist(c.getGridPoint());
        } else {
            return -1;
        }

    }

    private void setTargetMouse(Creature c) {
        if (c == null) {
            this.lab = LAB_YELLOW;
            this.targetMouse = null;
            return;
        }
        if (c instanceof Mouse) {
            this.targetMouse = (Mouse) c;
            this.lab = LAB_CYAN;
            return;
        }

    }

    private void dirToTarget(Mouse target) {
        int vertDist = Math.abs(this.getY() - target.getY());
        int horizDist = Math.abs(this.getX() - target.getX());
        // if vertical distance strictly greater than horiz

        if (horizDist < vertDist) {
            if (target.getY() < this.getY()) {
                this.setDir(0);
                return;
            } else {
                this.setDir(2);
                return;
            }
        } else {
            if (this.getX() < target.getX()) {
                this.setDir(1);
                return;
            } else {
                this.setDir(3);
                return;
            }
        }
    }

    private void killCatMaybe() {
        // cat hasnt eaten mouse in 50 moves
        if (movesSinceMouseEaten == 50) {
            this.dead = true;
        }
    }

    private void checkToEat() {
        // Check if any mouse is at current x and y, to EAT
        if (targetMouse == null) {
            return;
        }
        if (this.getGridPoint().equals(targetMouse.getGridPoint())) {
            targetMouse.dead = true;
            movesSinceMouseEaten = 0;
            return;
        }
    }

    private void maybeTurn() {
        int randomNum = city.getNextRandomTurn();
        if (randomNum == 0) {
            this.randomTurn();
        }
    }
}
