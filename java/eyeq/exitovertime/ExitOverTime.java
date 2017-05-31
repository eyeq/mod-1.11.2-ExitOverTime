package eyeq.exitovertime;

import eyeq.exitovertime.event.ExitOverTimeEventHandler;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

import static eyeq.exitovertime.ExitOverTime.MOD_ID;

@Mod(modid = MOD_ID, version = "1.0", dependencies = "after:eyeq_util")
public class ExitOverTime {
    public static final String MOD_ID = "eyeq_exitovertime";

    @Mod.Instance(MOD_ID)
    public static ExitOverTime instance;

    public static int toExitSec;
    public static int notifyIntervalSec;
    public static int countDownSec;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(new ExitOverTimeEventHandler());
        load(new Configuration(event.getSuggestedConfigurationFile()));
    }

    public static void load(Configuration config) {
        config.load();

        String category = "Int";
        toExitSec = config.get(category, "toExitSec", 1980).getInt();
        notifyIntervalSec = config.get(category, "notifyIntervalSec", 600).getInt();
        countDownSec = config.get(category, "countDownSec", 10).getInt();
    	
        if(config.hasChanged()) {
            config.save();
        }
    }
}
