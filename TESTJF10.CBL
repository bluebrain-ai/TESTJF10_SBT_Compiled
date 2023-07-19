	   IDENTIFICATION  DIVISION.
	   PROGRAM-ID.	   TESTJF10.
	   AUTHOR.		   SYSTEM.
	   DATE-WRITTEN.   06/11/2003.
	   DATE-COMPILED.  06/11/2003.
	   
	  
	   ENVIRONMENT DIVISION.
	   
	   CONFIGURATION SECTION.
	   SOURCE-COMPUTER. IBM-9672.
	   OBJECT-COMPUTER. IBM-9672.
	   
	   INPUT-OUTPUT SECTION.
	   FILE-CONTROL.
	   
	        

	        SELECT OUTPUT01-FILE ASSIGN TO OUT01
				   FILE STATUS IS WS-OUT01-STATUS.
				   
	        SELECT OUTPUT02-FILE ASSIGN TO OUT02
				   FILE STATUS IS WS-OUT02-STATUS.
				   
	        SELECT OUTPUT03-FILE ASSIGN TO OUT03
				   FILE STATUS IS WS-OUT03-STATUS.
				   
	        SELECT OUTPUT04-FILE ASSIGN TO OUT04
				   FILE STATUS IS WS-OUT04-STATUS.				   
				   
				   
       DATA DIVISION.
	   
	   FILE SECTION.
	   
	 
	   FD   OUTPUT01-FILE
			  LABEL RECORDS ARE STANDARD 
			  RECORDING MODE IS F.
 
       01   REC-OUT01                   PIC X(896).
	   
      

       FD   OUTPUT02-FILE 
              LABEL RECORDS ARE STANDARD 
              RECORDING MODE IS F.

       01   REC-OUT02                   PIC X(30).

      

       FD   OUTPUT03-FILE 
	          LABEL RECORDS ARE STANDARD 
			  RECORDING MODE IS F.
			  
	   01   REC-OUT03                   PIC X(80). 
	   
	  
	  
	   FD   OUTPUT04-FILE
	          LABEL RECORDS ARE STANDARD 
			  RECORDING MODE IS F. 
			 
	   01   REC-OUT04                   PIC X(80).
	   
	   WORKING-STORAGE SECTION.
	   
	   01   FILE-STATUS-CODES.          
	        
       	    05 WS-OUT01-STATUS          PIC X(02).   
		    05 WS-OUT02-STATUS          PIC X(02).
            05 WS-OUT03-STATUS		    PIC X(02).
		    05 WS-OUT04-STATUS          PIC X(02).
			05 ISMR121                  PIC X(7) 
			                                     VALUE 'ISMR121'.
			05  WS-ROUTINE              PIC X(10).
            05  WS-INPUT-DATE           PIC X(10).
            05  WS-PARM2-NUM            PIC 9(03).
            05  WS-PARM3                PIC X(10).
            05  WS-PARM4                PIC X(10).
            05  WS-PARM5                PIC X(10).  
       
       01   DIS-SQL                     PIC -(5)9(3).      
      
       01   WS-SWITCHES.
            05 WS-PARKANB-SWITCH  	    PIC X(01) VALUE 'Y'. 
			   88 WS-MORE-PARKANB                 VALUE 'Y'.
    		   88 WS-NO-MORE-PARKANB              VALUE 'N'.
	        05 WS-PARKANB-CL-SWITCH        PIC X(01) VALUE 'Y'.
               88 WS-MORE-PARKANB-CL              VALUE 'Y'.
               88 WS-NO-MORE-PARKANB-CL    	      VALUE 'N'.
			05 WS-FOUND-IK              PIC X(01) VALUE 'N'.
			   88 WS-NOT-FOUND-IK                 VALUE 'N'.
			05 WS-FOUND-ES              PIC X(01  VALUE 'N'.
			   88 WS-NOT-FOUND-ES                 VALUE 'N'. 
			05 WS-FOUND-IS              PIC X(01) VALUE 'N'.
			   88 WS-NOT-FOUND-IS                 VALUE 'N'.
            05 WS-FOUND-DL              PIC X(01) VALUE 'N'.
			   88 WS-NOT-FOUND-DL                 VALUE 'N'.
            05 WS-FOUND-DP              PIC X(01) VALUE 'N'.
               88 WS-NOT-FOUND-DP                 VALUE 'N'.
            
            05  WS-SUPP-FOUND           PIC X(01).
                88  SUPP-FOUND                    VALUE 'Y'.
                88  SUPP-NOT-FOUND                VALUE 'N'.
            05  WS-ORD-MTD-FOUND        PIC X(01).	
                88  ORD-MTD-FOUND                 VALUE 'Y'.
                88  ORD-MTD-NOT-FOUND             VALUE 'N'.
           
            05 WS-PARAMETER             PIC X(02) VALUE SPACES.
    
       01   WS-DATE-REFORMAT-AREAS. 
            05 WS-WORK-DATE.
               10 WS-WORK-DATE-CCYY     PIC X(4).
               10 DASH-1                PIC X(1).
               10 WS-WORK-DATE-MM       PIC X(2).
               10 DASH-2                PIC X(1).
               10 WS-WORK-DATE-DD       PIC X(2).
            05 WS-CCYYMMDD.		 
               10 WS-CCYY-DATE          PIC X(4).
               10 WS-MM-DATE            PIC X(2).
               10 WS-DD-DATE            PIC X(2).
            05 WS-DATE                  PIC X(10).
            05 WS-CURRENT-DATE          PIC X(10) VALUE '0000-00-00'.
            05 WS-TODAY-DATE            PIC X(08).
            05 WS-TODAY-TIME            PIC X(06).
            05 WS-START-DATE            PIC X(10) VALUE '0000-00-00'.

       01   WS-COUNTERS.
            05 WS-BLANK-TALLY           PIC 9(02) VALUE 0.
            05 WS-CL-COUNT              PIC 9(02) VALUE 0.
            05 WS-OUT01-COUNTER         PIC 9(08) VALUE 0.
            05 WS-OUT02-COUNTER         PIC 9(08) VALUE 0.  			
			05 WS-OUT03-COUNTER         PIC 9(08) VALUE 0.
            05 WS-OUT04-COUNTER         PIC 9(08) VALUE 0.  
			05 WS-IN03-DISPLAY-COUNT    PIC ZZ,ZZZ,ZZ9. 
			05 WS-IN05-DISPLAY-COUNT    PIC ZZ,ZZZ,ZZ9.
			05 WS-OUT01-DISPLAY-COUNT   PIC ZZ,ZZZ,ZZ9.
            05 WS-OUT02-DISPLAY-COUNT	PIC ZZ,ZZZ,ZZ9.
            05 WS-OUT03-DISPLAY-COUNT   PIC ZZ,ZZZ,ZZ9.
            05 WS-OUT04-DISPLAY-COUNT   PIC ZZ,ZZZ,ZZ9.
            05 T01CNT                   PIC 9(03) VALUE 0.
            05 T02CNT                   PIC 9(03) VALUE 0.
			05 I03CNT                   PIC 9(03) VALUE 0.
			05 I05CNT                   PIC 9(03) VALUE 0. 
			05 WS-SUP-CNT               PIC 9(03) VALUE 0.
			05 WS-ORD-CNT               PIC 9(03) VALUE 0. 
			
	   
	   01   T1-SUPP-CODE    PIC X(05).
		    88 VALID-SUPP-CODE  VALUE '00001' '00002' '00003' '00004' '00005'.
					 
       
	   01   T2-ORDER-METHOD  PIC X(01).
	        88 VALID-ORDER-METHOD VALUE 'A' 'B' 'C' 'D' 'E'. 
					
	   01   HOST-VARIABLES-PM. 
            05 HV-PM-CUSTOMER-SUPP      PIC X(07).
            05 HV-PM-LOCATION           PIC X(12).
            05 HV-PM-ITEMID             PIC X(12).
            05 HV-PM-EFF-START          PIC X(10).
            05 HV-PM-LOT-QUANTITY       PIC S9(9) COMP.
            05 HV-PM-KANBAN             PIC X(04).
            05 HV-PM-EMPLOYEE           PIC X(05).
            05 HV-PM-ORDER-METHOD       PIC X(01).
            05 HV-PM-LIFE-CYCLE         PIC X(01).
            05 HV-COUNT-LOCATION        PIC S9(05) COMP.
            05 HV-COUNT-STORADD         PIC S9(05) COMP.
            05 HV-PART-DESCRIPTION-CNT  PIC S9(05) COMP.
            05 HV-SUPP-PLANT-CNT        PIC S9(05) COMP.
			 
	   01   NULL-IND. 
	        05 NI-LOT-QUANTITY          PIC S9(4) COMP.
			05 NI-KANBAN                PIC S9(4) COMP.
			05 NI-VEHICLE-CODE          PIC S9(4) COMP.
			05 NI-COMMENT               PIC S9(4) COMP.
			05 NI-STOR-ADDR             PIC S9(4) COMP.
			05 NI-PK-LOCATION           PIC S9(4) COMP.
			05 NI-CNT-LOC               PIC S9(4) COMP.
			05 NI-CNT-STOR              PIC S9(4) COMP.
			05 NI-PK-PACKING-STYLE      PIC S9(4) COMP.
			05 NI-PK-STORE-ADDR-PRIM    PIC S9(4) COMP. 
			
	   01   HOST-VARIABLES-SM.         
            05 HV-SM-SUPP-PLANT-NAME    PIC X(50).	   
			05 HV-SM-PART-DESCRIPTION   PIC X(40).
			05 HV-SM-VEHICLE-CODE       PIC X(05).
			05 HV-SM-COMMENT            PIC X(40).
       
	   01   HOST-VARIABLES-PK.
	        05 HV-PK-LOCATION           PIC X(12). 
			05 HV-PK-STORE-ADDR-PRIM    PIC X(10).
			05 HV-PK-PACKING-STYLE      PIC X(10).
			05 HV-PK-SHARE              PIC S9(3)V9(4) USAGE COMP-3.
			
       01   WS-PART-NUMBER              PIC X(12).
	   01   WS-CUSTOMER-SUPP            PIC X(07).
	   01   WS-BUSINESS-ENT             PIC X(02) VALUE SPACES.
	   01   WS-DOCK                     PIC X(02).
	   01   WS-KANBAN                   PIC X(04).
	   
	   01   WS-QTY-PER-BOX              PIC 9(07).
	   01   WS-PART-NO-WORK             PIC X(10).
	   
	   01   HOST-VARIABLES-SP. 
	        05 HV-SP-PERIOD             PIC X(08).
			
	   01   01REC.
	        05 01-OWK-BUSINESS-ENTITY     PIC X(05) VALUE SPACES.
			05 01-OWK-ORD-REL-TYPE-CODE   PIC X(02) VALUE SPACES.
			05 01-OWK-ORD-REL-STATUS      PIC X(02) VALUE SPACES.
			05 01-OWK-ORD-REL-DT          PIC X(14) VALUE SPACES.
			05 01-OWK-BE-DOCK             PIC X(02) VALUE SPACES.
			05 01-OWK-DOCK-DESC           PIC X(30) VALUE SPACES.
			05 01-OWK-ORD-NUM 			  PIC X(12) VALUE SPACES.
			05 01-OWK-ORD-SERIES-NUM      PIC X(08) VALUE SPACES.
			05 01-OWK-ORD-DEL-NUM         PIC X(04) VALUE SPACES.
			05 01-OWK-SUP-PLANT-CODE      PIC X(05) VALUE SPACES.
			05 01-OWK-SUP-PLANT-NAME      PIC X(30) VALUE SPACES.
			05 01-OWK-ORD-SPECIALIST      PIC X(02) VALUE SPACES.
			05 01-OWK-ORD-HDL-TYPE-CODE   PIC X(01) VALUE SPACES.
			05 01-OWK-BE-UNLOAD-DT        PIC X(14) VALUE SPACES.
			05 01-OWK-RP-CHECKSHEET-PRINT PIC X(14) VALUE SPACES.
			05 01-OWK-RP-PICKLIST-PRINT   PIC X(14) VALUE SPACES.
			05 01-OWK-RP-SKIDMFST-PRINT   PIC X(14) VALUE SPACES.
			05 01-OWK-PU-ROUTE-START-DT   PIC X(14) VALUE SPACES.
			05 01-OWK-SUP-ARV-DT          PIC X(14) VALUE SPACES.
			05 01-OWK-SUP-SHIP-DOCK       PIC X(03) VALUE SPACES.
			05 01-OWK-DATA.       
  			   10 01-OWK-PARTNER-CODE     PIC X(05) VALUE SPACES.
			   10 01-OWK-PARTNER-NAME     PIC X(30) VALUE SPACES.
			   10 01-OWK-PNT-CODE         PIC X(05) VALUE SPACES.
			   10 01-OWK-PNT-SHORT-NAME   PIC X(03) VALUE SPACES.
			   10 01-OWK-PNT-NAME         PIC X(30) VALUE SPACES.
			   10 01-OWK-RESP-LP-FLAG     PIC X(01) VALUE SPACES.
			   10 01-OWK-RTE-CODE         PIC X(09) VALUE SPACES.
			   10 01-OWK-RTE-DEPART-DT    PIC X(14) VALUE SPACES.
			   10 01-OWK-RTE-ARRIVE-DT    PIC X(14) VALUE SPACES.
			05 01-OWK-KANBAN-NUM          PIC X(04) VALUE SPACES.
			05 01-OWK-PART-NUM            PIC X(12) VALUE SPACES.
			05 01-OWK-PART-DESC           PIC X(30) VALUE SPACES.
			05 01-OWK-QTY-PER-BOX         PIC X(07) VALUE SPACES.
			05 01-OWK-LAST-BOX-QTY        PIC X(07) VALUE SPACES.
			05 01-OWK-TTL-BOX-ORD         PIC X(07) VALUE SPACES.
			05 01-OWK-BO-REMAIN-TO-ORD    PIC X(07) VALUE SPACES.
			05 01-OWK-STORE-ADDRESS       PIC X(10) VALUE SPACES.
            05 01-OWK-LINESIDE-ADDRESS    PIC X(10) VALUE SPACES.
            05 01-OWK-NAMC-DATA           PIC X(10) VALUE SPACES.
			05 01-OWK-FILLER              PIC X(15) VALUE SPACES.  
			
	   01   02-DATETIME. 
	        05 02-PROCESS-DATE           PIC X(08).
			05 02-PROCESS-TIME           PIC X(06).
			05 02-FILLER1                PIC X(66).
			
       01   03-ERRREC.  
            05  03-OWK-PART-NUM          PIC X(12).
            05  03-FILLER1               PIC X(01).
            05  03-OWK-KANBAN-NUM        PIC X(04).
            05  03-FILLER2			     PIC X(01).
			05  03-OWK-BE-DOCK           PIC X(02).
			05  03-FILLER3               PIC X(01).
			05  03-OWK-SUP-PLANT-CODE    PIC X(05).
			05  03-FILLER4               PIC X(01).
			05  03-ERROR-MESSAGE         PIC X(53).

       01   04-CNTREC.
            05 04-OWK-BUS-ENT            PIC X(05).
            05 04-OWK-REL-TYPE           PIC X(02).
            05 04-OWK-DATE-TIME          PIC X(14).
            05 04-OWK-NUM-RECS           PIC 9(10).
            05 04-FILLER3                PIC X(40).

       01   HOST-VARIABLES-PP.         
            05 HV-PP-PERIOD-TYPE       PIC X(02).
            05 HV-PP-PERIOD       	   PIC X(08).
            
            EXEC SQL 
                  INCLUDE SQLCA 
            END-EXEC.
            
            EXEC SQL 
                DECLARE C1-PARKANB CURSOR FOR 
                  SELECT  CUSTOMER_SUPP,				
			              LOCATION,
			              EMPLOYEE,
			              KANBAN,
			              ITEMID,
			              LOT-QUANTITY,
			              EFF_START,
			              ORDER_METHOD 
			      FROM    PARKANB 
                  WHERE   TYPE = 'CD'				  
			      AND     CSI_TYPE = 'SU'
				         (EFF_START <= :WS-START-DATE AND 
						 (EFF_STOP  >= :WS-CURRENT-DATE OR 
						  EFF_STOP IS NULL))
				  ORDER BY CUSTOMER-SUPP,    
			               LOCATION,
						   ITEMID,
			               EFF-START 
			END-EXEC.
			
			
            EXEC SQL 
                DECLARE C2-PARKANB CURSOR FOR 
                  SELECT  SHARE,				
				          LOCATION,
						  STORE_ADDRESS_PRIM,
						  PACKING_STYLE 
				  FROM  PARKANB
                  WHERE TYPE = 'CL' 
                   AND   CSI_TYPE             = 'SU' 
                   AND   ITEMID               = :WS-PART-NUMBER 
                   AND   CUSTOMER_SUPP        = :WS-CUSTOMER-SUPP 
                   AND   SUBSTR(LOCATION,1,2) = :WS-DOCK 
                   AND   (EFF_START <= :WS-START-DATE AND 
                         (EFF_STOP  >= :WS-CURRENT-DATE OR 
                          EFF_STOP IS NULL))
                  ORDER BY SHARE DESC, LOCATION DESC 
            END-EXEC.	
            
            			
	    PROCEDURE DIVISION.
		
		0000-MAIN-MODULE.
		
			DISPLAY 'OWKB010 START'.
			
			PERFORM 1000-INITIALIZATION THRU
					1000-INITIALIZATION-EXIT.
			
			PERFORM 2000-MAINLINE THRU
					2000-MAINLINE-EXIT.
					
			PERFORM 3000-CLOSE-FILES THRU
					3000-CLOSE-FILES-EXIT.
					
			GOBACK.
			
		0000-MAIN-MODULE0EXIT.  EXIT.
	   
	    
	   
	    1000-INITIALIZATION.
		
			
			OPEN OUTPUT OUTPUT01-FILE.
			IF WS-OUT01-STATUS NOT = '00'
			   DISPLAY 'PROGRAM NAME : OWKB010'
			   DISPLAY 'OPEN ERROR FOR OUTPUT01 FILE'
			   DISPLAY 'FILE STATUS : ' WS-OUT01-STATUS
			   CALL 'ABENDME'
			END-IF.
			
			
			ACCEPT WS-TODAY-DATE FROM DATE YYYYMMDD.
			
			MOVE WS-TODAY-DATE TO WS-CCYYMMDD 
								  02-PROCESS-DATE
			
			ACCEPT WS-TODAY-TIME FROM TIME .
			
			MOVE 02-DATETIME TO  01-OWK-ORD-REL-DT
								 04-OWK-DATE-TIME.
								 
			MOVE WS-TODAY-TIME TO 02-PROCESS-TIME.
			
			MOVE WS-CCYY-DATE 	TO WS-WORK-DATE-CCYY.
			MOVE WS-MM-DATE		TO WS-WORK-DATE-MM.
			MOVE WS-DD-DATE		TO WS-WORK-DATE-DD.
			MOVE '-'			TO DASH-1 DASH-2.
			MOVE WS-WORK-DATE 	TO WS-CURRENT-DATE.
			
			MOVE 'BUMPWORK'				TO WS-ROUTINE.
			MOVE WS-CURRENT-DATE		TO WS-INPUT-DATE
			MOVE 20						TO WS-PARM2-NUM
			MOVE '+'					TO WS-PARM3
			MOVE SPACES					TO WS-PARM4
			MOVE SPACES					TO WS-PARM5
			CALL ISMR121 USING WS-ROUTINE
							   WS-INPUT-DATE
							   WS-PARM2-NUM
							   WS-PARM3
							   WS-PARM4
							   WS-PARM5.
			IF WS-PARM5 = SPACES
			   DISPLAY 'PROGRAM NAME : OWKB010'
			   DISPLAY 'FORCED ABEND - CALENDAR ROUTINE ABEND'
			   CALL 'ABENDME'
			END-IF
			MOVE WS-PARM5 TO WS-START-DATE.
			DISPLAY 'WS START DATE: ' WS-START-DATE.
			
			PERFORM 8100-WRITE-DATETIME THRU
					8100-WRITE-DATETIMR-EXIT .
					
       1000-INITIALIZATION-EXIT. EXIT.
	 
	  
	  	  
	   2000-MAINLINE.
	   
		    *>CE Group to var #.
		    MOVE 02-DATETIME TO 01-OWK-PART-DESC.
			
			*>CE VAR to Group   #.
		    MOVE  01-OWK-PART-DESC TO 02-DATETIME.
		   
		   MOVE 'BK005'  			TO 01-OWK-BUSINESS-ENTITY
									   04-OWK-BUS-ENT
		   MOVE 'DO'         		TO 01-OWK-ORD-REL-TYPE-CODE
									   04-OWK-REL-TYPE
		    
		   MOVE 'RP'  TO  01-OWK-ORD-REL-STATUS
		    
		   MOVE I1-ORDER-HANDLE-TYPE TO 01-OWK-ORD-HDL-TYPE-CODE
		   
		   MOVE SPACES 	        	TO 01-OWK-NAMC-DATA. 
		   		   
		   PERFORM 4000-LOAD-SUPP-CODES THRU
				   4000-LOAD-SUPP-CODES-EXIT.
				   
		   PERFORM 4200-LOAD-ORDER-METHODS THRU
				   4200-LOAD-ORDER-METHODS-EXIT.
				   
		   PERFORM 2100-OPEN-C1-PARKANB THRU
			       2100-OPEN-C1-PARKANB-EXIT.
					  
		   PERFORM 2200-MAIN-PROCESS-LOOP THRU
		           2200-MAIN-PROCESS-LOOP-EXIT
			 UNTIL WS-NO-MORE-PARKANB.
		   
			 
	   2000-MAINLINE-EXIT. EXIT.
	  
	  	  
	   2100-OPEN-C1-PARKANB.
	   
		   EXEC SQL
				OPEN C1-PARKANB
		   END-EXEC.
		   
		   EVALUATE SQLCODE
			   WHEN 0
					CONTINUE
			   WHEN OTHER
					DISPLAY 'BAD OPEN ON C1-PARKANB'
					MOVE SQLCODE TO DIS-SQL
					DISPLAY  'SQL CODE = ' DIS-SQL
					CALL 'ABENDME'
		   END-EVALUATE.
		   
	   2100-OPEN-C1-PARKANB-EXIT. EXIT.
	  
	  
	   
	   2200-MAIN-PROCESS-LOOP.
		  
		   EXEC SQL
		      FETCH C1-PARKANB INTO
					:HV-PM-CUSTOMER-SUPP,
					:HV-PM-LOCATION,
					:HV-PM-EMPLOYEE,
					:HV-PM-KANBAN:NI-KANBAN,
					:HV-PM-ITEMID,
					:HV-PM-LOT-QUANTITY:NI-LOT-QUANTITY,
					:HV-PM-EFF-START,
					:HV-PM-ORDER-METHOD
		   END-EXEC.
		   
		   EVALUATE SQLCODE
		       WHEN 0
			          SET SUPP-NOT-FOUND TO TRUE
					  SET ORD-MTD-NOT-FOUND TO TRUE
					  PERFORM 2205-LOOK-FOR-SUPPLIER THRU
							  2205-LOOK-FOR-SUPPLIER-EXIT
					  PERFORM 2207-LOOK-FOR-ORD-METD THRU
							  2207-LOOK-FOR-ORD-METD-EXIT
					  IF  SUPP-NOT-FOUND
					  AND ORD-MTD-NOT-FOUND
						  PERFORM 2210-MOVE-REFORMAT THRU
								  2210-MOVE-REFORMAT-EXIT
					  END-IF
		       WHEN 100
					MOVE 'N' TO WS-PARKANB-SWITCH
			   WHEN OTHER
					DISPLAY 'BAD FETCH ON C1-PARKANB'
					MOVE SQLCODE TO DIS-SQL
					DISPLAY 'SQL CODE  = ' DIS-SQL
					DISPLAY 'START DATE =  ' WS-START-DATE
					DISPLAY 'CURR DATE  = ' WS-CURRENT-DATE
					CALL 'ABENDME'
		   END-EVALUATE.
		   
	   2200-MAIN-PROCESS-LOOP-EXIT. EXIT.
	   
	   
	   
	   2205-LOOK-FOR-SUPPLIER.
	       
		   MOVE HV-PM-CUSTOMER-SUPP TO T1-SUPP-CODE
		   IF VALID-SUPP-CODE
			   SET   SUPP-FOUND TO TRUE 
			   
		   END-IF. 
		
	
	  
	   
       2205-LOOK-FOR-SUPPLIER-EXIT. EXIT. 
	   
	   2207-LOOK-FOR-ORD-METD.
	   
	       MOVE HV-PM-ORDER-METHOD   TO  T2-ORDER-METHOD.
		   
		   IF VALID-ORDER-METHOD
			   SET   ORD-MTD-FOUND TO TRUE
		   END-IF.
		
       2207-LOOK-FOR-ORD-METD-EXIT. EXIT.
       
       2210-MOVE-REFORMAT. 

           MOVE SPACES        TO 01REC. 
           

		   MOVE HV-PM-ITEMID              TO 01-OWK-PART-NUM 
		                                    
           MOVE HV-PM-KANBAN              TO 01-OWK-KANBAN-NUM 
                                             
           
		   
		   MOVE HV-PM-EMPLOYEE             TO 01-OWK-ORD-SPECIALIST 
		   MOVE HV-PM-LOCATION       (1:2) TO 01-OWK-BE-DOCK 
		                                      
		   MOVE HV-PM-CUSTOMER-SUPP  (3:5) TO 01-OWK-SUP-PLANT-CODE 
		                                    
		   MOVE 02-DATETIME TO  01-OWK-ORD-REL-DT 
		                        
		   
		   
		      MOVE HV-PM-LOT-QUANTITY TO WS-QTY-PER-BOX 
			  MOVE WS-QTY-PER-BOX     TO 01-OWK-QTY-PER-BOX 
		  
		      PERFORM 2212-CSIPLNT-READ THRU 
			          2212-CSIPLNT-READ-EXIT. 
			  PERFORM 2215-PROCESS-PARDESC THRU  
			          2215-PROCESS-PARDESC-EXIT. 
			  PERFORM 2218-PROCESS-PARKANB-CL THRU
                      2218-PROCESS-PARKANB-CL-EXIT		
           
		   PERFORM 8000-WRITE-PARTMSTR THRU 
		           8000-WRITE-PARTMSTR-EXIT .
				 
       2210-MOVE-REFORMAT-EXIT. EXIT. 
	   
	   2212-CSIPLNT-READ.
	   
	       MOVE ZEROS TO HV-SUPP-PLANT-CNT. 
		   EXEC SQL 
		       SELECT A.NAME, B.CNT 
			     INTO :HV-SM-SUPP-PLANT-NAME, 
				      :HV-SUPP-PLANT-CNT 
			     FROM CSIPLNT A, 
				 ( SELECT COUNT(DISTICT(NAME)) AS CNT  
				 FROM  CSIPLNT
				WHERE PLANT_TYPE = 'PC'
				  AND CUSTOMER_SUPP = :HV-PM-CUSTOMER-SUPP) AS B
				WHERE PLANT_TYPE = 'PC' 
				  AND CUSTOMER-SUPP = :HV-PM-CUSTOMER-SUPP 
		   END-EXEC. 
		   
		   EVALUATE SQLCODE 
		       WHEN 0 
			        MOVE HV-SM-SUPP-PLANT-NAME (1:30) TO 
					        01-OWK-SUP-PLANT-NAME 
				    IF  HV-SUPP-PLANT-CNT > 1 
				        MOVE 'MULTIPLE SUPPLIER PLANT NAME' 
				        TO 03-ERROR-MESSAGE 
				        PERFORM 8200-WRITE-ERROR THRU 
						        8200-WRITE-ERROR-EXIT  
			        END-IF 
		        WHEN 100 
			        MOVE 'NO DATA' TO 01-OWK-SUP-PLANT-NAME 
			        MOVE 'NO SUPPLIER PLANT NAME' TO 03-ERROR-MESSAGE 
			        PERFORM 8200-WRITE-ERROR THRU 
				            8200-WRITE-ERROR-EXIT 
		   WHEN OTHER 
			    DISPLAY 'BAD SELECT ON CSIPLNT' 
			    MOVE SQLCODE TO DIS-SQL 
			    DISPLAY 'SQL CODE = ' DIS-SQL 
				CALL 'ABENDME' 
	       END-EVALUATE.
		
       2212-CSIPLNT-READ-EXIT. EXIT.
	  
       
	   2215-PROCESS-PARDESC.
	   
	       MOVE ZEROS TO HV-PART-DESCRIPTION-CNT .
		   EXEC SQL 
		       SELECT A.DESCRIPTION, B.CNT 
			     INTO :HV-SM-PART-DESCRIPTION, 
				      :HV-PART-DESCRIPTION-CNT 
				 FROM PARDESC A, 
				 ( SELECT COUNT(DISTINCT(DESCRIPTION) AS CNT 
				 FROM    PARDESC 
				 WHERE ITEMID = :HV-PM-ITEMID) AS B  
				 WHERE A.ITEMID = :HV-PM-ITEMID 
		    END-EXEC. 
			
			EVALUATE SQLCODE 
			    WHEN 0 
				    MOVE HV-SM-PART-DESCRIPTION(1:30) TO 
					        01-OWK-PART-DESC 
					IF  HV-PART-DESCRIPTION-CNT > 1
					    MOVE 'MULTIPLE PART DESCRIPTION' 
						TO 03-ERROR-MESSAGE 
						PERFORM 8200-WRITE-ERROR THRU 
						        8200-WRITE-ERROR-EXIT 
					END-IF 
				WHEN 100 
                    MOVE 'NO DATA' TO 01-OWK-PART-DESC 
                    MOVE 'NO PART DESCRIPTION' TO 03-ERROR-MESSAGE
                    PERFORM 8200-WRITE-ERROR THRU 
                            8200-WRITE-ERROR-EXIT 
                WHEN OTHER 
                     DISPLAY 'BAD SELECT ON PARDESC' 
                     MOVE SQLCODE TO DIS-SQL 
                     DISPLAY 'SQL CODE = ' DIS-SQL 
                     CALL 'ABENDME' 
            END-EVALUATE.
			
       2215-PROCESS-PARDESC-EXIT. EXIT.

       
	   
	   2218-PROCESS-PARKANB-CL.
	      
		   MOVE HV-PM-ITEMID 			TO WS-PART-NUMBER 
		   MOVE HV-PM-LOCATION(1:2) 	TO     WS-DOCK
		   MOVE HV-PM-CUSTOMER-SUPP 	TO     WS-CUSTOMER-SUPP
		   MOVE HV-PM-KANBAN 			TO WS-KANBAN  
		   MOVE SPACES 					TO HV-PK-LOCATION 
										   HV-PK-STORE-ADDR-PRIM.
           MOVE ZEROS                  TO HV-COUNT-LOCATION  
										   HV-COUNT-STORADD. 
		   
		   EXEC SQL 
		        OPEN C2-PARKANB 
		   END-EXEC.
		   
		    EVALUATE SQLCODE
		        WHEN 0
                     CONTINUE 
                WHEN OTHER  
                     DISPLAY 'BAD OPEN ON C2-PARKANB' 
                     MOVE SQLCODE TO DIS-SQL 
                     DISPLAY 'SQL CODE = ' DIS-SQL 
                     CALL 'ABENDME' 
            END-EVALUATE.
 
            EXEC SQL 
                FETCH C2-PARKANB 
                 INTO :HV-PK-SHARE,
                      :HV-PK-LOCATION :NI-PK-LOCATION,		 
				      :HV-PK-STORE-ADDR-PRIM :NI-PK-STORE-ADDR-PRIM, 
					  :HV-PK-PACKING-STYLE   :NI-PK-PACKING-STYLE
			END-EXEC.
            
            EVALUATE SQLCODE 
                WHEN 0 
                     MOVE HV-PK-LOCATION (3:10) 
                                              TO 01-OWK-LINESIDE-ADDRESS 
                     MOVE HV-PK-STORE-ADDR-PRIM 
 					                          TO 01-OWK-STORE-ADDRESS 
				     MOVE HV-PK-PACKING-STYLE (1:1)  
                                              TO 01-OWK-NAMC-DATA
                     MOVE HV-PK-PACKING-STYLE (2:2)													
                                              TO 01-OWK-NAMC-DATA
		             
				WHEN 100  
                     MOVE 'NO DATA' TO 01-OWK-LINESIDE-ADDRESS  
					  
					
                     MOVE 'NO DATA' TO 01-OWK-STORE-ADDRESS  
					  
					
					 MOVE SPACES   TO  01-OWK-NAMC-DATA
                                       
                      
			    WHEN OTHER 
				     DISPLAY 'BAD FETCH ON C2-PARKANB CURSOR' 
                     MOVE SQLCODE TO DIS-SQL 
                     DISPLAY 'SQL CODE = ' DIS-SQL 				 
                     CALL 'ABENDME' 
            END-EVALUATE.
            
            EXEC SQL 
                 CLOSE C2-PARKANB 
            END-EXEC.
            			
			EVALUATE SQLCODE		 
                WHEN 0 
				     CONTINUE 
				WHEN OTHER 
				     DISPLAY 'BAD CLOSE ON C2-PARKANB'
                     MOVE SQLCODE TO DIS-SQL 
                     DISPLAY 'SQL CODE = ' DIS-SQL 
                     CALL 'ABENDME' 
           END-EVALUATE.
  
       2218-PROCESS-PARKANB-CL-EXIT. EXIT. 
       
       
	   
	   3000-CLOSE-FILES.
	   
		       EXEC SQL 
			        CLOSE C1-PARKANB 
			   END-EXEC 
			   
			   EVALUATE SQLCODE
                   WHEN 0
                        CONTINUE 
                   WHEN OTHER 
                        DISPLAY 'BAD CLOSE ON C1-PARKANB'  
                        MOVE SQLCODE TO DIS-SQL 
                        DISPLAY 'SQL CODE = ' DIS-SQL 
                        CALL 'ABENDME' 
               END-EVALUATE						
	         
		  
		   CLOSE OUTPUT01-FILE.
		   
		   IF WS-OUT01-STATUS NOT = '00'
   		      DISPLAY 'PROGRAM NAME : OWKB010'
			  DISPLAY 'CLOSE ERROR FOR OUTPUT FILE 1 '
			  DISPLAY 'FILE STATUS ' WS-OUT01-STATUS
		      CALL 'ABENDME'
		   END-IF.
	  
		   MOVE WS-OUT01-COUNTER	TO WS-OUT01-DISPLAY-COUNT.
		  
		   DISPLAY 'OWKB010 EXTRACT RECORDS WRITTEN = '
					WS-OUT01-DISPLAY-COUNT.
		   
		   
	   3000-CLOSE-FILES-EXIT.  EXIT.
	   
	   
	   8000-WRITE-PARTMSTR.
	   
		   WRITE REC-OUT01 FROM 01REC.
		   
		   IF WS-OUT01-STATUS NOT = '00'
			  DISPLAY 'PROGRAM NAME : OWKB010'
			  DISPLAY 'WRITE ERROR FOR OUTPUT FILE 1 '
			  DISPLAY 'FILE STATUS ' WS-OUT01-STATUS
			  CALL 'ABENDME'
		   ELSE
			  ADD 1 TO WS-OUT01-COUNTER
		   END-IF.
		   
	   8000-WRITE-PARTMSTR-EXIT. EXIT.