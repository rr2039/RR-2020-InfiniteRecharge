package frc.robot;

public enum hopperState{
    Init{
        @Override
        public hopperState nextState() {
            return Hot;
        }
    }, 
    Hot{
        @Override
        public hopperState nextState() {
            return Armed;
        }
    }, 
    Armed{
        @Override
        public hopperState nextState() {
            return Shoot;
        }
    }, 
    Shoot{
        @Override
        public hopperState nextState() {
            return Init;
        }
    };

    public abstract hopperState nextState();
}