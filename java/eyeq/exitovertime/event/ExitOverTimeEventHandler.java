package eyeq.exitovertime.event;

import eyeq.exitovertime.ExitOverTime;
import eyeq.util.client.MinecraftUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ExitOverTimeEventHandler {
    private long start;
    private int countdown;
    private int prevNotify;

    @SubscribeEvent
    public void onClientTickTick(TickEvent.ClientTickEvent event) {
        GuiScreen gui = Minecraft.getMinecraft().currentScreen;
        if(gui != null && gui.getClass() == GuiMainMenu.class) {
            start = 0;
            return;
        }
        if(start == 0) {
            start = System.currentTimeMillis();
            countdown = -1;
            prevNotify = ExitOverTime.notifyIntervalSec;
            return;
        }

        long now = System.currentTimeMillis();
        int elapsed = (int) ((now - start) / 1000);
        int remain = ExitOverTime.toExitSec - elapsed;

        if(remain <= ExitOverTime.countDownSec) {
            if(remain != countdown) {
                countdown = remain;
                ITextComponent text = new TextComponentString("Countdown: " + remain + "sec");
                Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(text);
            }
            if(remain <= 0) {
                MinecraftUtils.displayGameMenu();
            }
        } else if(elapsed >= prevNotify) {
            prevNotify += ExitOverTime.notifyIntervalSec;
            ITextComponent text = new TextComponentString("Elapsed: " + prevNotify + "sec");
            Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(text);
        }
    }
}
