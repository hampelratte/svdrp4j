/* $Id$
 * 
 * Copyright (c) Henrik Niehaus & Lazy Bones development team
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, 
 *    this list of conditions and the following disclaimer in the documentation 
 *    and/or other materials provided with the distribution.
 * 3. Neither the name of the project (Lazy Bones) nor the names of its 
 *    contributors may be used to endorse or promote products derived from this 
 *    software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package org.hampelratte.svdrp.mock;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server implements Runnable {

    private static transient Logger logger = LoggerFactory.getLogger(Server.class);
    
    private int port = 2001;
    
    private BufferedReader br;
    
    private BufferedWriter bw;
    
    private ServerSocket serverSocket;
    private Socket socket;
    
    private String welcome;
    private String timers;
    private String channels = "";
    
    public Server() {
        logger.info("Running in {}", System.getProperty("user.dir"));
    }
    
    public void run() {
        serveClients();
    }
    
    public void loadWelcome(String welcomeTextFile) throws IOException {
        welcome = readFile(welcomeTextFile);
    }
    
    public void loadTimers(String timersFile) throws IOException {
        timers = readFile(timersFile);
    }
    
    public void loadChannelsConf(String channelsFile) throws IOException {
        String channelsConf = readFile(channelsFile);
        StringTokenizer st = new StringTokenizer(channelsConf, "\n");
        int channelNumber = 0;
        while(st.hasMoreElements()) {
            String line = st.nextToken();
            char delim = st.hasMoreElements() ? '-' : ' ';
            channels += "250" + delim + Integer.toString(++channelNumber) + ' ' + line;
            if(st.hasMoreElements()) {
                channels += '\n';
            }
        }
    }

    private String readFile(String textFile) throws IOException {
        logger.debug("Trying to load file {}", textFile);
        InputStream in = getClass().getResourceAsStream("/" + textFile);
        Scanner scanner = new Scanner(in, "UTF-8");
        StringBuilder sb = new StringBuilder();
        while(scanner.hasNext()) {
            sb.append(scanner.nextLine());
            if(scanner.hasNext()) {
                sb.append('\n');
            }
        }
        return sb.toString();
    }

    private void serveClients() {
        try {
            serverSocket = new ServerSocket(port);
            logger.debug("Listening on port {}", port);
            while (!serverSocket.isClosed()) {
                logger.info("Waiting for connection from client");
                socket = serverSocket.accept();
                socket.setSoTimeout((int) TimeUnit.MINUTES.toMillis(3));
                logger.info("Connection from {}", socket.getRemoteSocketAddress());

                br = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
                bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), "UTF-8"));

                logger.debug("Sending welcome message.");
                sendWelcomeMessage();

                while (!socket.isClosed()) {
                    try {
                        if (!readRequest()) {
                            break;
                        }
                    } catch (SocketTimeoutException e) {
                        logger.error("Read timeout. Closing connection {}", socket.getRemoteSocketAddress());
                        socket.close();
                    }
                }

            }
        } catch (SocketException e) {
            if(serverSocket != null && !serverSocket.isClosed()) {
                logger.error("Error while serving clients", e);
            }
        } catch (IOException e) {
            logger.error("Error while serving clients", e);
        }
    }

    private boolean readRequest() throws IOException {
        if(socket.isClosed()) {
            return false;
        }
            
        String request = br.readLine();
        
        logger.debug("<-- {}", request);
        if(request == null) return false;
        
        if("quit".equalsIgnoreCase(request)) {
            sendResponse("221 vdr closing connection");
            socket.close();
        } else if ("lstc".equalsIgnoreCase(request)) {
            printChannelList();
        } else if ("lstt".equalsIgnoreCase(request)) {
            sendResponse(timers);
        } else if ("lstr".equalsIgnoreCase(request)) {
            printRecordingsList();
        } else if (request.toLowerCase().matches("lste \\d+.*")) {
            printEpg();
        } else if ("test_charset".equalsIgnoreCase(request)) {
            sendResponse("221 öüäß");
        } else {
            sendResponse("502 Not implemented");
        }
        
        return true;
    }

    private void printEpg() throws IOException {
        //String lste = readFile("lste_1.txt");
        String lste = readFile("lste_empty.txt");
        sendResponse(lste);
    }

    private void sendResponse(String resp) throws IOException {
        logger.debug("--> {}", resp);
        bw.write(resp);
        bw.write('\n');
        bw.flush();
    }

    private void printRecordingsList() throws IOException {
        sendResponse("250-1 13.05.07 14:45* %Das Wunder von Lengede - Teil 2\n" + 
                "250-2 22.05.10 00:30  Frei Schnauze XXL\n" + 
                "250-3 19.05.10 21:40  Hart aber fair\n" + 
                "250-4 27.04.08 22:07* %Banditen!\n" + 
                "250-5 24.08.08 00:55* %Das Vermächtnis des geheimen Buches\n" + 
                "250-6 05.04.10 22:30* Knockin' On Heaven's Door\n" + 
                "250-7 22.02.09 02:19  Die Frauen von Stepford\n" + 
                "250-8 07.09.08 22:10  %Verführung einer Fremden (Perfect Stranger)\n" + 
                "250-9 21.02.10 01:15* Das Gelbe vom Ei\n" + 
                "250-10 05.05.10 20:10  Dresden\n" + 
                "250-11 13.05.10 20:10  Das Parfum - Die Geschichte eines Mörders\n" + 
                "250-12 21.05.10 20:55  Der Trödel-King\n" + 
                "250-13 15.06.09 22:40* Der steinerne Kreis\n" + 
                "250-14 10.05.10 21:06  FlashForward\n" + 
                "250-15 20.05.10 00:05* FlashForward\n" + 
                "250-16 01.05.08 20:30* %Das Wunder von Bern\n" + 
                "250-17 06.05.07 14:40* %Das Wunder von Lengede - Teil 1\n" + 
                "250-18 06.04.10 00:15* Der Eisbär\n" + 
                "250-19 11.08.09 20:10  Corellis Mandoline\n" + 
                "250-20 24.02.06 03:40  %Sophie Scholl - Die letzten Tage\n" + 
                "250-21 19.06.06 09:15  %Flightplan - Ohne jede Spur\n" + 
                "250-22 22.11.09 20:10* Der Baader Meinhof Komplex (1/2)\n" + 
                "250-23 06.04.10 01:50* Der Tunnel (1)\n" + 
                "250-24 07.02.10 20:09  Men in Black II\n" + 
                "250-25 04.02.06 22:05* %Natural Born Killers\n" + 
                "250-26 30.05.09 22:10  Butterfly Effect\n" + 
                "250-27 23.05.10 15:50* Mythbusters - Die Wissensjäger\n" + 
                "250-28 06.04.10 03:20  Der Tunnel (2)\n" + 
                "250-29 21.05.10 22:15* KDD - Kriminaldauerdienst\n" + 
                "250-30 22.05.10 21:35* KDD - Kriminaldauerdienst\n" + 
                "250-31 23.11.09 20:10* Der Baader Meinhof Komplex (2/2)\n" + 
                "250-32 01.11.09 23:55  The Sentinel - Wem kannst du trauen?\n" + 
                "250 33 01.03.09 16:35  Schöne Venus");
    }

    private void printChannelList() throws IOException {
        sendResponse(channels); 
//        ps.println("250-1 Das Erste;ARD:11836:hC34:S19.2E:27500:101:102=deu,103=2ch;106=dd:104:0:28106:1:1101:0\n" + 
//              "250-2 ZDF;ZDFvision:11954:hC34:S19.2E:27500:110:120=deu,121=2ch;125=dd:130:0:28006:1:1079:0\n" + 
//              "250-3 SWR Fernsehen BW;ARD:11836:hC34:S19.2E:27500:801:802=deu:804:0:28113:1:1101:0\n" +  
//              "250-4 RTL Television,RTL;RTL World:12188:hC34:S19.2E:27500:163:104=deu;106=deu:105:0:12003:1:1089:0\n" + 
//              "250-5 RTL2;RTL World:12188:hC34:S19.2E:27500:166:128=deu:68:0:12020:1:1089:0\n" + 
//              "250-6 ProSieben;ProSiebenSat.1:12544:hC56:S19.2E:22000:511:512=deu;515=deu:33:0:17501:1:1107:0\n" +
//              "250-7 Bayerisches FS Süd;ARD:11836:hC34:S19.2E:27500:201:202=deu,203=2ch;206=dd:204:0:28107:1:1101:0\n" +
//              "250-8 kabel eins;ProSiebenSat.1:12544:hC56:S19.2E:22000:767:768=deu:34:0:17502:1:1107:0\n" +
//              "250-9 KiKa;ZDFvision:11954:hC34:S19.2E:27500:310:320=deu:330:0:28008:1:1079:0\n" +
//              "250-10 TELE 5;BetaDigital:12480:vC34:S19.2E:27500:1535:1536=deu:38:0:51:133:33:0\n" + 
//              "250-11 Super RTL,S RTL;RTL World:12188:hC34:S19.2E:27500:165:120=deu:65:0:12040:1:1089:0\n" + 
//              "250-12 3sat;ZDFvision:11954:hC34:S19.2E:27500:210:220=deu,221=2ch;225=dd:230:0:28007:1:1079:0\n" + 
//              "250-13 MDR S-Anhalt;ARD:12110:hC34:S19.2E:27500:2901:2902=deu,2903=2ch:2904:0:28229:1:1073:0\n" + 
//              "250-14 NDR FS HH;ARD:12110:hC34:S19.2E:27500:2601:2602=deu,2603=2ch:2604:0:28225:1:1073:0\n" + 
//              "250-15 Radio Bremen TV-alt;ARD:12266:hC34:S19.2E:27500:1201:1202=deu:1204:0:28485:1:1093:0\n" + 
//              "250-16 rbb Berlin;ARD:12110:hC34:S19.2E:27500:601:602=deu:604:0:28206:1:1073:0\n" + 
//              "250-17 SR Fernsehen;ARD:12266:hC34:S19.2E:27500:1301:1302=deu:1304:0:28486:1:1093:0\n" + 
//              "250-18 SAT.1;ProSiebenSat.1:12544:hC56:S19.2E:22000:255:256=deu;259=deu:32:0:17500:1:1107:0\n" +
//              "250-19 WDR Köln;ARD:11836:hC34:S19.2E:27500:601:602=deu:604:0:28111:1:1101:0\n" + 
//              "250-20 EinsExtra;ARD:10743:hC56:S19.2E:22000:101:102=deu:0:0:28721:1:1051:0\n" + 
//              "250-21 EinsFestival;ARD:10743:hC56:S19.2E:22000:201:202=deu:204:0:28722:1:1051:0\n" + 
//              "250-22 EinsPlus;ARD:10743:hC56:S19.2E:22000:301:302=deu:304:0:28723:1:1051:0\n" + 
//              "250-23 arte;ARD:10743:hC56:S19.2E:22000:401:402=deu,403=fra:404:0:28724:1:1051:0\n" + 
//              "250-24 Phoenix;ARD:10743:hC56:S19.2E:22000:501:502=deu:504:0:28725:1:1051:0\n" + 
//              "250-25 ZDFdokukanal;ZDFvision:11954:hC34:S19.2E:27500:660:670=deu,671=2ch:630:0:28014:1:1079:0\n" + 
//              "250-26 ZDFinfokanal;ZDFvision:11954:hC34:S19.2E:27500:610:620=deu:630:0:28011:1:1079:0\n" + 
//              "250-27 ZDFtheaterkanal;ZDFvision:11954:hC34:S19.2E:27500:1110:1120=deu:630:0:28016:1:1079:0\n" + 
//              "250-28 ZDF;T-Systems:11054:hC56:S13.0E:27500:570:571=deu:572:0:8011:318:12700:0\n" + 
//              "250-29 PREMIERE HD,PREM HD;PREMIERE:11914:hC910E35M42:S19.2E:27500:767:0;771=deu,772=eng:32:1830,1833,9C4,1801:129:133:6:0\n" + 
//              "250-30 DISCOVERY HD,DISC HD;PREMIERE:11914:hC910E35M42:S19.2E:27500:1023:0;1027=deu:32:1830,1833,9C4,1801:130:133:6:0\n" + 
//              "250-31 PREMIERE FILMCLASSICS,FILMCLASSICS;PREMIERE:11720:hC34:S19.2E:27500:1023:1024=deu:32:1702,1722,1830,1833,9C4,1801:41:133:3:0\n" + 
//              "250-32 PREMIERE FILMFEST,FILMFEST;PREMIERE:11720:hC34:S19.2E:27500:1279:1280=deu:32:1702,1722,1830,1833,9C4,1801:20:133:3:0\n" + 
//              "250-33 PREMIERE KRIMI,KRIMI;PREMIERE:11798:hC34:S19.2E:27500:2815:2816=deu:32:1702,1722,1830,1833,9C4,1801:23:133:2:0\n" + 
//              "250-34 PREMIERE NOSTALGIE,NOSTALGIE;PREMIERE:11720:hC34:S19.2E:27500:1535:1536=deu:32:1702,1722,1830,1833,9C4,1801:516:133:3:0\n" + 
//              "250-35 PREMIERE SERIE,SERIE;PREMIERE:11798:hC34:S19.2E:27500:2559:2560=deu;2563=deu:32:1702,1722,1830,1833,9C4,1801:16:133:2:0\n" + 
//              "250-36 Deutsches Gesundheitsfernsehen;BetaDigital:12460:hC34:S19.2E:27500:1023:1024=deu:0:0:663:133:5:0\n" + 
//              "250-37 DrDish Television;BetaDigital:12246:vC34:S19.2E:27500:2047:2048=deu:0:0:10107:1:1092:0\n" + 
//              "250-38 Mallorca.TV;BetaDigital:12246:vC34:S19.2E:27500:1487:1488=deu:0:0:10169:1:1092:0\n" + 
//              "250-39 Franken SAT,FSAT;BetaDigital:12246:vC34:S19.2E:27500:1279:1280=deu:0:0:10104:1:1092:0\n" + 
//              "250-40 münchen.tv/RFO,mütv/RFO;BetaDigital:12246:vC34:S19.2E:27500:1791:1792=deu:0:0:10106:1:1092:0\n" + 
//              "250-41 rtn myestate,rtn;BetaDigital:12148:hC34:S19.2E:27500:1279:1280=deu,1281=eng:0:0:767:133:7:0\n" + 
//              "250-42 rhein main tv;MEDIA BROADCAST:12633:hC56:S19.2E:22000:208:308=deu:508:0:12614:1:1113:0\n" + 
//              "250-43 Bahn TV;MEDIA BROADCAST:12633:hC56:S19.2E:22000:201:301=deu:501:0:12600:1:1113:0\n" + 
//              "250-44 Juwelo TV;MEDIA BROADCAST:12633:hC56:S19.2E:22000:1041:1042=deu:0:0:12616:1:1113:0\n" + 
//              "250-45 GIGA;BetaDigital:12460:hC34:S19.2E:27500:1007:1008=deu:0:0:776:133:5:0\n" + 
//              "250-46 DMAX;BetaDigital:12246:vC34:S19.2E:27500:511:512=deu:32:0:10101:1:1092:0\n" + 
//              "250-47 tv.gusto;BetaDigital:12460:hC34:S19.2E:27500:3071:3072=deu:0:0:659:133:5:0\n" + 
//              "250-48 NICK AUSTRIA;MTV Networks:12226:hC34:S19.2E:27500:513:661=deu:577:0:28640:1:1091:0\n" + 
//              "250-49 RNF;BetaDigital:12148:hC34:S19.2E:27500:1104:1105=deu:38:0:768:133:7:0\n" + 
//              "250-50 Daystar Television Network,Daystar Television;BetaDigital:12460:hC34:S19.2E:27500:3567:3568=deu:0:0:658:133:5:0\n" + 
//              "250-51 COMEDY CENTRAL Germany;MTV Networks Europe:11973:vC34:S19.2E:27500:4071+8190:4072:4074:0:28677:1:1078:0\n" + 
//              "250-52 1-2-3.tv;BetaDigital:12460:hC34:S19.2E:27500:2815:2816=deu:32:0:662:133:5:0\n" + 
//              "250-53 RTL Shop;RTL World:12188:hC34:S19.2E:27500:168:137:70:0:12080:1:1089:0\n" + 
//              "250-54 Q TV SHOP;BetaDigital:12148:hC34:S19.2E:27500:3071:3072=deu:0:0:54:133:7:0\n" + 
//              "250-55 Der Schmuckkanal,Schmuck;BetaDigital:12148:hC34:S19.2E:27500:3583:3584=deu:33:0:775:133:7:0\n" + 
//              "250-56 HSE24 Digital;SES Astra:12226:hC34:S19.2E:27500:512:660=deu:576:0:31210:1:1091:0\n" + 
//              "250-57 9Live;ProSiebenSat.1:12544:hC56:S19.2E:22000:1279:1280=deu:36:0:17504:1:1107:0\n" + 
//              "250-58 K-TV;MEDIA BROADCAST:12633:hC56:S19.2E:22000:202:302=deu:502:0:12601:1:1113:0\n" + 
//              "250-59 GOD Channel,GOD;BetaDigital:12148:hC34:S19.2E:27500:767:768=deu:0:0:774:133:7:0\n" + 
//              "250-60 .;BetaDigital:12460:hC34:S19.2E:27500:255:256=deu:0:0:766:133:5:0\n" + 
//              "250-61 Voyages Television,Voyages;BetaDigital:12148:hC34:S19.2E:27500:1023:1024=deu:37:0:769:133:7:0\n" + 
//              "250-62 dhd24.tv;Media Broadcast:12633:hC56:S19.2E:22000:53:54=deu:55:0:12604:1:1113:0\n" + 
//              "250-63 JAMBA! TV,JAMBA!;BetaDigital:12460:hC34:S19.2E:27500:2303:2304=deu:0:0:1794:133:5:0\n" + 
//              "250-64 Gebrauchtwagen TV;BetaDigital:12246:vC34:S19.2E:27500:2799:2800=deu:38:0:10124:1:1092:0\n" + 
//              "250-65 DAS VIERTE,D VIERTE;BetaDigital:12460:hC34:S19.2E:27500:2047:2048=deu:36:0:1793:133:5:0\n" +
//              "250-66 BR-alpha*;ARD:11836:hC34:S19.2E:27500:701:702=deu:704:0:28112:1:1101:0\n" + 
//              "250-67 hr-fernsehen;ARD:11836:hC34:S19.2E:27500:301:302=deu:304:0:28108:1:1101:0\n" + 
//              "250-68 MDR Sachsen;ARD:12110:hC34:S19.2E:27500:2901:2902=deu,2903=2ch:2904:0:28228:1:1073:0\n" + 
//              "250-69 VOX;RTL World:12188:hC34:S19.2E:27500:167:136=deu:71:0:12060:1:1089:0\n" +
//              "250-70 n-tv;RTL World:12188:hC34:S19.2E:27500:169:73=deu:80:0:12090:1:1089:0\n" + 
//              "250-71 N24;ProSiebenSat.1:12544:hC56:S19.2E:22000:1023:1024=deu:35:0:17503:1:1107:0\n" + 
//              "250-72 Bloomberg TV Germany;Bloomberg:12551:vC56:S19.2E:22000:162:99=deu:0:0:12160:1:1108:0\n" + 
//              "250-73 Bloomberg TV Germany:12552:vC56:S19.2E:22000:162:99=deu:0:0:12160:1:1108:1\n" + 
//              "250-74 BBC World;BBC:11597:vC56:S19.2E:22000:163:92=eng:41:0:10050:1:1026:0\n" + 
//              "250-75 Eurosport;SES Astra:12226:hC34:S19.2E:27500:101:103=deu:102:0:31200:1:1091:0\n" + 
//              "250-76 EuroNews;Globecast:12226:hC34:S19.2E:27500:2432:2433=deu,2434=eng,2435=fra,2436=ita,2437=esl,2438=por,2439=rus,2440=ara:0:0:31220:1:1091:0\n" + 
//              "250-77 DSF;BetaDigital:12480:vC34:S19.2E:27500:1023:1024=deu:39:0:900:133:33:0\n" + 
//              "250-78 ASTRA HD+;BetaDigital:11914:hC910E35M42:S19.2E:27500:1279:0;1283=deu:0:0:131:133:6:0\n" + 
//              "250-79 ANIXE HD;BetaDigital:11914:hC910E35M42:S19.2E:27500:1535:0;1539=deu:0:0:132:133:6:0\n" + 
//              "250-80 arte HD;ZDFvision:11361:hC23E35M8:S19.2E:22000:6210:6221=deu,6222=fra:6230:0:11120:1:1011:0\n" + 
//              "250-81 FRANCE 2 HD;CSAT:12610:vC56:S19.2E:22000:171:124=fra;126=fra:0:100:9611:1:1112:0\n" + 
//              "250-82 TVP HD - test;TVP:10773:hC56:S19.2E:22000:517:0;701=pol:0:0:17126:1:1053:0\n" + 
//              "250-83 MTV Germany;MTV Networks Europe:11973:vC34:S19.2E:27500:4031+8190:4032:4034:0:28673:1:1078:0\n" + 
//              "250-84 VIVA Germany;MTV Networks Europe:11973:vC34:S19.2E:27500:4061+8190:4062:4064:0:28676:1:1078:0\n" + 
//              "250-85 DELUXE MUSIC,DELUXE;BetaDigital:12246:vC34:S19.2E:27500:255:256=deu;259=deu:0:0:10100:1:1092:0\n" + 
//              "250-86 MTV AUSTRIA;MTV Networks:12227:hC34:S19.2E:27500:515:662=deu:578:0:28641:1:1091:0\n" + 
//              "250-87 imusic TV;BetaDigital:12460:hC34:S19.2E:27500:495:496=deu:0:0:772:133:5:0\n" + 
//              "250-88 GoTV;GoTV:12662:hC56:S19.2E:22000:1020:1021=deu:179:0:13102:1:1115:0\n" + 
//              "250-89 YAVIDO CLIPS,Y CLIPS;BetaDigital:12148:hC34:S19.2E:27500:239:240=deu:0:0:765:133:7:0\n" + 
//              "250-90 Das Erste;ARD:113000:C0M64:C:6900:101:102=deu,103=2ch;106=dd:104:0:28106:1:1101:0\n" + 
//              "250-91 ZDF;ZDFvision:626000:C0M64:C:6900:110:120=deu,121=2ch;125=dd:130:0:28006:1:1079:0\n" + 
//              "250-92 Sat. 1;Digital Free:330000000:M64:C:6875:2701:2702=deu;2703=deu:2704:1801,1722:53626:61441:10008:0\n" + 
//              "250-93 RTL Television;Digital Free:346000:C0M256:C:6900:1401:1402=deu;1403=deu:1404:1801,1722:53601:61441:10007:0\n" + 
//              "250-94 RTL II;Digital Free:346000:C0M256:C:6900:1601:1602=deu:1604:1801,1722:53603:61441:10007:0\n" + 
//              "250-95 VOX;Digital Free:346000:C0M256:C:6900:1701:1702=deu:1704:1801,1722:53604:61441:10007:0\n" + 
//              "250-96 ProSieben;Digital Free:330000000:M64:C:6875:2201:2202=deu;2203=deu:2204:1801,1722:53621:61441:10008:0\n" + 
//              "250-97 kabel eins;Digital Free:330000000:M64:C:6875:2301:2302=deu:2304:FFFF,1722:53622:61441:10008:0\n" + 
//              "250-98 Super RTL;Digital Free:346000:C0M256:C:6900:1501:1502=deu:1504:1801,1722:53602:61441:10007:0\n" + 
//              "250-99 n-tv;Digital Free:346000:C0M256:C:6900:1901:1902=deu:1904:1801,1722:53606:61441:10007:0\n" + 
//              "250-100 Eurosport;Digital Free:346000:C0M256:C:6900:2921:2922=deu:2924:1801,1722:53607:61441:10007:0\n" + 
//              "250-101 Das Vierte;Digital Free:346000:C0M256:C:6900:2931:2932=deu:2934:1801,1722:53608:61441:10007:0\n" + 
//              "250-102 Viva;Digital Free:346000:C0M256:C:6900:2941:2942=deu:2944:1801,1722:53609:61441:10007:0\n" + 
//              "250-103 Comedy Central;Digital Free:346000:C0M256:C:6900:2901:2902=deu:2904:1801,1722:53628:61441:10007:0\n" + 
//              "250-104 Tele 5;Digital Free:330000000:M64:C:6875:411:412=deu:414:1801,1722:53002:61441:10008:0\n" + 
//              "250-105 9Live;Digital Free:330000000:M64:C:6875:2401:2402=deu:2404:1801,1722:53623:61441:10008:0\n" + 
//              "250-106 DSF;Digital Free:330000000:M64:C:6875:2501:2502=deu:2504:1801,1722:53624:61441:10008:0\n" + 
//              "250-107 N24;Digital Free:330000000:M64:C:6875:2801:2802=deu:2804:1801,1722:53627:61441:10008:0\n" + 
//              "250-108 HSE24;Digital Free:330000000:M64:C:6875:2311:2312=deu:2314:1801,1722:53630:61441:10008:0\n" + 
//              "250-109 Bayerisches FS Süd;ARD:113000:C0M64:C:6900:201:202=deu,203=2ch;206=dd:204:0:28107:1:1101:0\n" + 
//              "250-110 WDR Köln;ARD:113000:C0M64:C:6900:601:602=deu:604:0:28111:1:1101:0");
    }

    private void sendWelcomeMessage() throws IOException {
        sendResponse(welcome);
    }
    
    public void shutdown() throws IOException, InterruptedException {
        logger.info("Shutting down server...");
        if(serverSocket!= null) serverSocket.close();
        if(socket != null) socket.close();
        logger.info("Shutdown successful.");
    }
    
    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.loadWelcome("welcome-1.6.0_2-utf_8.txt");
        server.loadTimers("lstt_doppelpack_fake.txt");
        server.loadChannelsConf("pvrinput_channels.conf");
        new Thread(server).start();
    }
}