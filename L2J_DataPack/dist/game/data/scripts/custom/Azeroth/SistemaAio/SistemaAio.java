/*
 * Copyright (C) 2004-2015 L2J DataPack
 * 
 * This file is part of L2J DataPack.
 * 
 * L2J DataPack is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * L2J DataPack is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program. If not, see <http://www.gnu.org/licenses/>.
 */
package custom.Azeroth.SistemaAio;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.StringTokenizer;

import ai.npc.AbstractNpcAI;

import com.l2jserver.L2DatabaseFactory;
import com.l2jserver.gameserver.datatables.SkillData;
import com.l2jserver.gameserver.handler.AdminCommandHandler;
import com.l2jserver.gameserver.handler.IAdminCommandHandler;
import com.l2jserver.gameserver.model.L2Object;
import com.l2jserver.gameserver.model.TeleportWhereType;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.model.events.EventType;
import com.l2jserver.gameserver.model.events.ListenerRegisterType;
import com.l2jserver.gameserver.model.events.annotations.RegisterEvent;
import com.l2jserver.gameserver.model.events.annotations.RegisterType;
import com.l2jserver.gameserver.model.events.impl.character.OnCreatureZoneExit;
import com.l2jserver.gameserver.model.events.impl.character.player.OnPlayerLogin;
import com.l2jserver.gameserver.model.skills.Skill;
import com.l2jserver.gameserver.model.zone.type.L2TownZone;
import com.l2jserver.util.Rnd;

/**
 * @author Eze
 */
public class SistemaAio extends AbstractNpcAI implements IAdminCommandHandler
{
	private final Skill[] skills_id =
	{
		SkillData.getInstance().getSkill(1085, 3),
		SkillData.getInstance().getSkill(1304, 3),
		SkillData.getInstance().getSkill(1087, 3),
		SkillData.getInstance().getSkill(1354, 1),
		SkillData.getInstance().getSkill(1062, 2),
		SkillData.getInstance().getSkill(1243, 6),
		SkillData.getInstance().getSkill(1045, 6),
		SkillData.getInstance().getSkill(1048, 6),
		SkillData.getInstance().getSkill(1311, 6),
		SkillData.getInstance().getSkill(168, 3),
		SkillData.getInstance().getSkill(213, 8),
		SkillData.getInstance().getSkill(1007, 3),
		SkillData.getInstance().getSkill(1309, 3),
		SkillData.getInstance().getSkill(1552, 3),
		SkillData.getInstance().getSkill(1006, 3),
		SkillData.getInstance().getSkill(1229, 15),
		SkillData.getInstance().getSkill(1308, 3),
		SkillData.getInstance().getSkill(1253, 3),
		SkillData.getInstance().getSkill(1284, 3),
		SkillData.getInstance().getSkill(1009, 3),
		SkillData.getInstance().getSkill(1310, 4),
		SkillData.getInstance().getSkill(1363, 315),
		SkillData.getInstance().getSkill(1362, 1),
		SkillData.getInstance().getSkill(1397, 330),
		SkillData.getInstance().getSkill(1292, 6),
		SkillData.getInstance().getSkill(1078, 6),
		SkillData.getInstance().getSkill(307, 1),
		SkillData.getInstance().getSkill(276, 1),
		SkillData.getInstance().getSkill(309, 1),
		SkillData.getInstance().getSkill(274, 1),
		SkillData.getInstance().getSkill(275, 1),
		SkillData.getInstance().getSkill(272, 1),
		SkillData.getInstance().getSkill(273, 1),
		SkillData.getInstance().getSkill(311, 1),
		SkillData.getInstance().getSkill(366, 1),
		SkillData.getInstance().getSkill(365, 1),
		SkillData.getInstance().getSkill(310, 1),
		SkillData.getInstance().getSkill(271, 1),
		SkillData.getInstance().getSkill(1242, 3),
		SkillData.getInstance().getSkill(1257, 3),
		SkillData.getInstance().getSkill(1353, 3),
		SkillData.getInstance().getSkill(1391, 3),
		SkillData.getInstance().getSkill(1352, 1),
		SkillData.getInstance().getSkill(229, 7),
		SkillData.getInstance().getSkill(228, 3),
		SkillData.getInstance().getSkill(1077, 3),
		SkillData.getInstance().getSkill(1218, 130),
		SkillData.getInstance().getSkill(1059, 3),
		SkillData.getInstance().getSkill(1219, 33),
		SkillData.getInstance().getSkill(1217, 33),
		SkillData.getInstance().getSkill(1388, 3),
		SkillData.getInstance().getSkill(1389, 3),
		SkillData.getInstance().getSkill(1240, 3),
		SkillData.getInstance().getSkill(1086, 2),
		SkillData.getInstance().getSkill(1032, 330),
		SkillData.getInstance().getSkill(1073, 2),
		SkillData.getInstance().getSkill(1036, 2),
		SkillData.getInstance().getSkill(1035, 4),
		SkillData.getInstance().getSkill(1068, 3),
		SkillData.getInstance().getSkill(1003, 3),
		SkillData.getInstance().getSkill(1282, 2),
		SkillData.getInstance().getSkill(1356, 1),
		SkillData.getInstance().getSkill(1355, 1),
		SkillData.getInstance().getSkill(1357, 33),
		SkillData.getInstance().getSkill(1044, 3),
		SkillData.getInstance().getSkill(1191, 330),
		SkillData.getInstance().getSkill(1033, 330),
		SkillData.getInstance().getSkill(1189, 330),
		SkillData.getInstance().getSkill(1259, 330),
		SkillData.getInstance().getSkill(1306, 6),
		SkillData.getInstance().getSkill(234, 23),
		SkillData.getInstance().getSkill(1040, 3),
		SkillData.getInstance().getSkill(364, 1),
		SkillData.getInstance().getSkill(264, 1),
		SkillData.getInstance().getSkill(306, 1),
		SkillData.getInstance().getSkill(269, 1),
		SkillData.getInstance().getSkill(270, 1),
		SkillData.getInstance().getSkill(265, 1),
		SkillData.getInstance().getSkill(363, 1),
		SkillData.getInstance().getSkill(349, 1),
		SkillData.getInstance().getSkill(308, 1),
		SkillData.getInstance().getSkill(305, 1),
		SkillData.getInstance().getSkill(304, 1),
		SkillData.getInstance().getSkill(267, 1),
		SkillData.getInstance().getSkill(266, 1),
		SkillData.getInstance().getSkill(268, 1),
		SkillData.getInstance().getSkill(1390, 3),
		SkillData.getInstance().getSkill(1303, 2),
		SkillData.getInstance().getSkill(1204, 2),
		SkillData.getInstance().getSkill(1268, 4),
	};
	private final String[] VCOMMANDS =
	{
		"admin_set_aio",
		"admin_remove_aio"
	};
	
	public SistemaAio()
	{
		super(SistemaAio.class.getSimpleName(), "custom");
		load();
		AdminCommandHandler.getInstance().registerHandler(this);
	}
	
	private void load()
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement("DELETE FROM character_aio WHERE aio_time<?"))
		{
			ps.setLong(1, System.currentTimeMillis());
			ps.execute();
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@RegisterEvent(EventType.ON_CREATURE_ZONE_EXIT)
	@RegisterType(ListenerRegisterType.GLOBAL)
	public void AioEnter(OnCreatureZoneExit event)
	{
		L2PcInstance player = null;
		
		if (event.getCreature() instanceof L2PcInstance)
		{
			player = (L2PcInstance) event.getCreature();
		}
		
		if ((player != null) && player.isaio())
		{
			
			if (event.getZone().getClass().equals(L2TownZone.class))
			{
				player.teleToLocation(TeleportWhereType.TOWN);
				player.sendMessage("Aio no puede salir de la ciudad.");
			}
			
		}
	}
	
	@RegisterEvent(EventType.ON_PLAYER_LOGIN)
	@RegisterType(ListenerRegisterType.GLOBAL_PLAYERS)
	public void OnPlayerLogin(OnPlayerLogin event)
	{
		L2PcInstance player = event.getActiveChar();
		if (getAio(player))
		{
			player.sendMessage("Eres Aio.");
			for (Skill skill : skills_id)
			{
				player.addSkill(skill);
			}
			player.getAppearance().setVisibleName("[AIO] " + player.getName());
			player.getAppearance().setNameColor(Rnd.get(255), Rnd.get(255), Rnd.get(255));
			player.broadcastInfo();
		}
	}
	
	public static void main(String[] args)
	{
		new SistemaAio();
	}
	
	@Override
	public boolean useAdminCommand(String command, L2PcInstance activeChar)
	{
		L2Object target = activeChar.getTarget();
		L2PcInstance player = null;
		StringTokenizer st = new StringTokenizer(command, " ");
		String actualcommand = st.nextToken();
		String val = "";
		if (st.countTokens() >= 1)
		{
			val = st.nextToken();
		}
		if (target instanceof L2PcInstance)
		{
			player = (L2PcInstance) target;
		}
		else
		{
			return false;
		}
		if (actualcommand.equalsIgnoreCase(VCOMMANDS[0]))
		{
			try
			{
				player.setaio(Byte.parseByte(val));
				for (Skill skill : skills_id)
				{
					player.addSkill(skill);
				}
				player.getAppearance().setVisibleName("[AIO] " + player.getName());
				player.getAppearance().setNameColor(Rnd.get(255), Rnd.get(255), Rnd.get(255));
				player.broadcastInfo();
				player.sendMessage("Felicidades eres Aio!!");
				activeChar.sendMessage("Has dado Aio al target.");
				
			}
			catch (Exception e)
			{
				activeChar.sendMessage("FORMATO DE NUMERO ERROR.");
			}
			return true;
		}
		else if (actualcommand.equalsIgnoreCase(VCOMMANDS[1]))
		{
			player.removeaio();
			for (Skill skill : skills_id)
			{
				player.removeSkill(skill);
			}
			player.getAppearance().setVisibleName(player.getName());
			player.getAppearance().setNameColor(255, 255, 255);
			player.broadcastInfo();
			player.sendMessage("Aio ha sido removido.");
			activeChar.sendMessage("Has removido Aio al target.");
			return true;
		}
		return false;
	}
	
	public boolean getAio(L2PcInstance player)
	{
		try (Connection con = L2DatabaseFactory.getInstance().getConnection();
			PreparedStatement ps = con.prepareStatement("SELECT aio_time FROM character_aio WHERE char_id=?"))
		{
			ps.setInt(1, player.getObjectId());
			try (ResultSet rs = ps.executeQuery())
			{
				while (rs.next())
				{
					final long aio_time = rs.getLong("aio_time");
					if (System.currentTimeMillis() < aio_time)
					{
						player.setAio(true);
						return true;
					}
				}
			}
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	@Override
	public String[] getAdminCommandList()
	{
		return VCOMMANDS;
	}
}
