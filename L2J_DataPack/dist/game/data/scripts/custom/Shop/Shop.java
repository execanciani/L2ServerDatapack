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
package custom.Shop;

import ai.npc.AbstractNpcAI;

import com.l2jserver.gameserver.model.actor.L2Npc;
import com.l2jserver.gameserver.model.actor.instance.L2PcInstance;
import com.l2jserver.gameserver.network.serverpackets.NpcHtmlMessage;

/**
 * @author Eze
 */
public class Shop extends AbstractNpcAI
{
	private final int _npcId = 100010;
	
	private Shop()
	{
		super(Shop.class.getSimpleName(), "custom");
		addStartNpc(_npcId);
		addFirstTalkId(_npcId);
		addTalkId(_npcId);
	}
	
	@Override
	public String onAdvEvent(String event, L2Npc npc, L2PcInstance player)
	{
		if (event.equals("ver_items"))
		{
			return "veritems.htm";
		}
		if (event.equals("vender_items"))
		{
			String filename = "data/scripts/custom/Shop/venderitems.htm";
			final NpcHtmlMessage html = new NpcHtmlMessage();
			html.setFile(player.getHtmlPrefix(), filename);
			hmtl.replace();
		}
		
		return "";
	}
	
	@Override
	public String onFirstTalk(L2Npc npc, L2PcInstance player)
	{
		return "shop.htm";
	}
	
	public static void main(String[] args)
	{
		new Shop();
	}
}
