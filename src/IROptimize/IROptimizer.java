package IROptimize;

import IR.IRProgram;
import IROptimize.Mem2reg.Mem2Reg;
import IROptimize.RegAlloca.*;

public class IROptimizer {

    public IRProgram program;

    public IROptimizer(IRProgram program) {
        this.program = program;
    }

    public void visit(IRProgram program) {
        new Mem2Reg(program).work();
        new RegAllocation(program).work();
        // System.out.write(program.toString().getBytes());
    }

}
