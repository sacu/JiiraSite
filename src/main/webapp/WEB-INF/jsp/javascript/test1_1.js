// JavaScript Document
var arrs = [];
		function addItem(value){
			arrs.push(value)
			document.getElementById("show").innerHTML = arrs;
		}
		function drawLine(){
			var cavs = document.getElementById("cavs");//获取canvas对象
			var g = cavs.getContext("2d");//获取绘图对象，类似as3的graphics
			g.clearRect(0,0,cavs.width,cavs.height);
			g.strokeStyle = "#ff0000";
			g.fillStyle = "#BB00FF";
			g.lineWidth = 4;
			//g.lineCap = 5;
			g.moveTo(100, 100);//移动到起始位置
			g.lineTo(128, 185);//要划线到的位置
			g.lineTo(60, 133);//要划线到的位置
			g.lineTo(140, 133);//要划线到的位置
			g.lineTo(70, 185);//要划线到的位置
			g.fill();//用于填充
			g.closePath();//和下一行一个意思，回到起始点
			//g.lineTo(100, 100);//要划线到的位置
			g.stroke();
			
			//如果不加开始路径，颜色值会用最新的，beginPath用来刷新一次缓冲，执行之前的绘制
			//如果不改变颜色，则上边的stroke和下边的beginPath则不用加
			g.beginPath();
			g.strokeStyle = "#00BBFF";
			g.moveTo(150, 100);//移动到起始位置
			g.lineTo(300, 350);//移动到起始位置
			g.stroke();
			
			g.beginPath();
			g.fillStyle = "#00ff00";
			g.strokeStyle = "#00FFBB";
			g.rect(100, 200, 100, 100);
			g.fill();
			g.stroke();
			//strokeRect = rect + stroke
			g.strokeRect(50, 50, 100, 100);
			//fill+rect
			g.fillStyle = "#333333";
			g.fillRect(200, 100, 100, 100);
			//alert(Math.PI / 180 * 270 + "," + Math.PI / 180 * 90)
			g.beginPath();
			//html5 角度转弧度，画圆公式 Math.PI / 180 * (360 - angle)
			//arc详解(x, y, 半径, 开始角度, 是否逆时针)
			//角度转弧度公式：
			//逆时针公式：Math.PI / 180 * (360 - angle)
			//顺时针公式：Math.PI / 180 * angle
			g.fillStyle = "#00ff00";
			g.strokeStyle = "#ff0000";
			g.arc(190, 70, 30, 0, Math.PI * 2);
			g.fill();
			g.stroke();
			g.beginPath();
			var angle = 270;//角度
			g.lineWidth = 2;
			g.arc(90, 70, 30, 0, Math.PI / 180 * (360 - angle), true);
			g.closePath();
			g.stroke();
			
			//扇形
			angle = 90;
			g.beginPath();
			g.moveTo(250, 70);//移动到起始位置
			g.arc(250, 70, 30, 0, Math.PI / 180 * angle, false);
			g.fill();
			g.closePath();
			g.stroke();
			
			var arr = ["#FF0000|0.1|2", "#00FF00|0.2|2", "#0000FF|0.7|4"];
			drawCircleTable(g, arr);
		}
		function showCircle(){
			var cavs = document.getElementById("cavs");//获取canvas对象
			var g = cavs.getContext("2d");//获取绘图对象，类似as3的graphics
			g.clearRect(0,0,cavs.width,cavs.height);
			drawCircleTable(g, arrs);
		}
		function drawCircleTable(g, arr){
			var angle = 0;
			var endAngle = 0;
			var str;
			for(var i = 0; i < arr.length; ++i){
				str = arr[i];//.split("|")
				angle = endAngle;
				endAngle = endAngle + 360 * str;
				var a1=Math.round(Math.random()*0x1000000);
				var c1="00000".concat(a1.toString(16));
				var bg1 = "#"+c1.substr(c1.length-6,6);
				
				var a2=Math.round(Math.random()*0x1000000);
				var c2="00000".concat(a2.toString(16));
				var bg2 = "#"+c2.substr(c2.length-6,6);
				
				drawCircle(g, bg1, 2, bg2, 250, 250, 200, angle, endAngle, false);
			}
		}
		function drawCircle(g, lineColor, lineWidth, fillColor, x, y, radius, beginAngle, endAngle, anticlockwise){
			var angle1, angle2;
			if(anticlockwise){
				angle1 = Math.PI / 180 * (360 - beginAngle);
				angle2 = Math.PI / 180 * (360 - endAngle);
			} else {
				angle1 = Math.PI / 180 * beginAngle;
				angle2 = Math.PI / 180 * endAngle;
			}
			g.beginPath();
			g.strokeStyle = lineColor;
			g.lineWidth = lineWidth;
			g.fillStyle = fillColor;
			g.moveTo(x, y);//移动到起始位置
			g.arc(x, y, radius, angle1, angle2, anticlockwise);
			g.fill();
			g.closePath();
			g.stroke();
		}