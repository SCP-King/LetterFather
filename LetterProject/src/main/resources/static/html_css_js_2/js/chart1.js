$(document).ready(function() {
  'use strict';

    let x_data=[]
    let y_data=[]
    let  color1=[]
    let  color2=[]
    let y_data1=new Array(12).fill(0)
    let y_data2=new Array(12).fill(0)
    let y_data3=new Array(12).fill(0)


  $.ajax(
      {
          url:"/data1",
          method:"get",
            success:function (resp){
                let arr=resp;

          for(let i=0;i<arr.length;i++){
              if(arr[i].year===2021){
                  y_data1[arr[i].month]=arr[i].letter_num
              }
              else if(arr[i].year===2022){
                  y_data2[arr[i].month]=arr[i].letter_num
              }
              else {
                  y_data3[arr[i].month]=arr[i].letter_num
              }
          }
                var lineChartCanvas = $("#lineChart").get(0).getContext("2d");
                var lineChart = new Chart(lineChartCanvas, {
                    type: 'line',
                    data: data_1,
                    options: options
                });

            },
        error:function (){
          alert("获取数据失败，系统出错")
        }

      }
  )


  let data_1={
    labels:[1,2,3,4,5,6,7,8,9,10,11,12],
    datasets:[
        {
            label:"2021",
            data:y_data1,
            backgroundColor:"orange",
            borderColor:"orange",
            borderWidth: 1,
            fill:false,
            pointBackgroundColor: 'orange',
            pointBorderColor: 'orange',
            pointRadius: 2,
            pointHoverRadius: 4
        },
        {
            label:"2022",
            data:y_data2,
            backgroundColor:"green",
            borderColor:"green",
            borderWidth: 1,
            fill:false,
            pointBackgroundColor: 'green',
            pointBorderColor: 'green',
            pointRadius: 2,
            pointHoverRadius: 4
        },
        {
            label:"2023",
            data:y_data3,
            backgroundColor:"purple",
            borderColor:"purple",
            borderWidth: 1,
            fill:false,
            pointBackgroundColor: 'purple',
            pointBorderColor: 'purple',
            pointRadius: 2,
            pointHoverRadius: 4
        }
    ]
  }
    let options = {
            title: {
                display: true,
                text: '询问信件日期分布',
                fontSize: 16
            },
        responsive: true,
        animation: {
            animateScale: true,
            animateRotate: true
        },
        scales: {
            yAxes: [{
                ticks: {
                    beginAtZero: true
                }
            }]
        },
        legend: {
            display: true,
            position:"top"
        }

    };
});