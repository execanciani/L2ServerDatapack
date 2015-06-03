package custom.Azeroth.BugReport;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.StringTokenizer;
import java.util.logging.Logger;

import com.l2jserver.gameserver.cache.HtmCache;
import com.l2jserver.gameserver.model.L2World;
import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.quest.Quest;
import com.l2jserver.gameserver.network.L2GameClient;
import com.l2jserver.gameserver.network.clientpackets.Say2;
import com.l2jserver.gameserver.network.serverpackets.CreatureSay;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author -=DoctorNo=- Version 2.3
 */
public class BugReport extends Quest
{
	private final Logger _log = Logger.getLogger(BugReport.class.getName());
	private static final int NpcId = 553; // npc id here
	private static String htmlLoc = "data/scripts/custom/Azeroth/BugReport/1.htm";
	
	public BugReport(int questId, String name, String descr)
	{
		super(questId, name, descr);
		addFirstTalkId(NpcId);
		addTalkId(NpcId);
		addStartNpc(NpcId);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		System.out.println("onAdvEvent");
		if (event.startsWith("report"))
		{
			System.out.println("report");
			sendReport(event, npc, player, event);
		}
		return "";
	}
	
	private void sendReport(String event, L2Npc npc, L2PcInstance player, String command)
	{
		StringTokenizer st = new StringTokenizer(command);
		st.nextToken();
		
		String message = "";
		String _type = null; // General, Fatal, Misuse, Balance, Other
		L2GameClient info = player.getClient().getConnection().getClient();
		
		try
		{
			_type = st.nextToken();
			while (st.hasMoreTokens())
			{
				message = message + st.nextToken() + " ";
			}
			
			if (message.equals(""))
			{
				player.sendMessage("Message box cannot be empty.");
				return;
			}
			
			String fname = "data/BugReports/" + player.getName() + ".txt";
			File file = new File(fname);
			boolean exist = file.createNewFile();
			if (!exist)
			{
				player.sendMessage("You have already sent a bug report, GMs must check it first.");
				return;
			}
			
			FileWriter fstream = new FileWriter(fname);
			BufferedWriter out = new BufferedWriter(fstream);
			out.write("Character Info: " + info + "\r\nBug Type: " + _type + "\r\nMessage: " + message);
			player.sendMessage("Report sent. GMs will check it soon. Thanks...");
			
			for (L2PcInstance allgms : L2World.getInstance().getAllGMs())
			{
				allgms.sendPacket(new CreatureSay(0, Say2.SHOUT, "Bug Report Manager", player.getName() + " sent a bug report."));
				allgms.sendPacket(new CreatureSay(0, Say2.SHOUT, "Report Type", _type + "."));
			}
			
			_log.info("Character: " + player.getName() + " sent a bug report.");
			out.close();
		}
		catch (Exception e)
		{
			player.sendMessage("Something went wrong try again.");
		}
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		final int npcId = npc.getId();
		if (player.getQuestState(getName()) == null)
		{
			newQuestState(player);
		}
		
		if (npcId == NpcId)
		{
			String html = HtmCache.getInstance().getHtm(player.getHtmlPrefix(), htmlLoc);
			html = html.replaceAll("%player%", player.getName());
			
			NpcHtmlMessage npcHtml = new NpcHtmlMessage(0);
			npcHtml.setHtml(html);
			player.sendPacket(npcHtml);
		}
		return "";
	}
	
	public static void main(final String[] args)
	{
		new BugReport(-1, BugReport.class.getSimpleName(), "custom");
		System.out.println("CUSTOM: BugReport Manager loaded");
	}
}
