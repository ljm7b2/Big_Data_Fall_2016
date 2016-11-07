angular.module('app.controllers', ['PointService', 'chart.js', 'Q5TimeService'])
  
.controller('homeCtrl', function($scope, Points) {
           
    var map, heatmap;
    $scope.initMap = function(){
        map = new google.maps.Map(document.getElementById('map'), {
          zoom: 4,
          center: {lat: 39.8282, lng: -98.5795},
          mapTypeId: google.maps.MapTypeId.ROADMAP
        });

        heatmap = new google.maps.visualization.HeatmapLayer({
          data: $scope.getPoints(),
          map: map
        }); 
    }
    
    $scope.toggleHeatmap = function(){
        heatmap.setMap(heatmap.getMap() ? null : map);
    }
    
    $scope.changeGradient = function(){
        var gradient = [
          'rgba(0, 255, 255, 0)',
          'rgba(0, 255, 255, 1)',
          'rgba(0, 191, 255, 1)',
          'rgba(0, 127, 255, 1)',
          'rgba(0, 63, 255, 1)',
          'rgba(0, 0, 255, 1)',
          'rgba(0, 0, 223, 1)',
          'rgba(0, 0, 191, 1)',
          'rgba(0, 0, 159, 1)',
          'rgba(0, 0, 127, 1)',
          'rgba(63, 0, 91, 1)',
          'rgba(127, 0, 63, 1)',
          'rgba(191, 0, 31, 1)',
          'rgba(255, 0, 0, 1)'
        ]
        heatmap.set('gradient', heatmap.get('gradient') ? null : gradient);
    }
    
    $scope.changeRadius = function(){
        heatmap.set('radius', heatmap.get('radius') ? null : 20);
    }
    
    $scope.changeOpacity = function(){
        heatmap.set('opacity', heatmap.get('opacity') ? null : 0.2);
    }
    
    $scope.getPoints = function(){
        return Points.pts();
    }
    
    $scope.initMap();
})
   
.controller('aboutCtrl', function($scope, $ionicModal, Times) {
    
    
    $scope.getTimesTms = function(){
        return Times.tms();
    }
    $scope.getTimesCts = function(){
        return Times.cts();
    }
    
    $ionicModal.fromTemplateUrl('my-modal.html', {
    scope: $scope,
    animation: 'slide-in-up'
    }).then(function(modal) {
    $scope.modal = modal;
    });
    $scope.openModal = function() {
      console.log("here")
    $scope.modal.show();
    };
    $scope.closeModal = function() {
    $scope.modal.hide();
    };
    // Cleanup the modal when we're done with it!
    $scope.$on('$destroy', function() {
    $scope.modal.remove();
    });
    // Execute action on hide modal
    $scope.$on('modal.hidden', function() {
    // Execute action
    });
    // Execute action on remove modal
    $scope.$on('modal.removed', function() {
    // Execute action
    });
    
//    Q1 data
    $scope.labels1 = ['Hilary Clinton', 'Tim Kaine', 'Donald Trump', 'Mike Pence'];
    $scope.series1 = ['Total Tweet Mentions Per Candidate'];
     $scope.type1 = 'polarArea';
    
    $scope.datasetOverride1 = [
        { 
          backgroundColor: [
            "#FF6384",
            "#4BC0C0",
            "#FFCE56",
            "#E7E9ED",
        ],
        }
    ];
    
    $scope.toggle = function () {
      $scope.type = $scope.type === 'polarArea' ?
        'pie' : 'polarArea';
    };

    $scope.data1 = [
        [60162, 191, 112210, 352]
    ];
    $scope.options1 = {
        responsive: true,
        maintainAspectRatio:true,
        legend: {
            display:true,
            labels:{
                fontColor: 'rgb(255, 99, 132)'
            },
            position:'bottom'
        },
         title: {
            display: true,
            text: 'Out of all the tweets collected, which candidate is being discussed the most?',
            fontSize:16,
        },
        scales:{
            yAxes:[{
                ticks:{
                    min:0
                }
            }]
        }
    }
    
//   Q2 Data  
    
    $ionicModal.fromTemplateUrl('my-modal2.html', {
    scope: $scope,
    animation: 'slide-in-up'
    }).then(function(modal) {
    $scope.modal2 = modal;
    });
    $scope.openModal2 = function() {
      console.log("here")
    $scope.modal2.show();
    };
    $scope.closeModal2 = function() {
    $scope.modal2.hide();
    };
    // Cleanup the modal when we're done with it!
    $scope.$on('$destroy', function() {
    $scope.modal2.remove();
    });
    // Execute action on hide modal
    $scope.$on('modal.hidden', function() {
    // Execute action
    });
    // Execute action on remove modal
    $scope.$on('modal.removed', function() {
    // Execute action
    });
    
    $scope.labels2 = ['1 Tweet', '2 Tweets', '3 Tweets','4 Tweets','5 Tweets','6 Tweets','7 Tweets','8 Tweets','9 Tweets','10 Tweets','11 Tweets','12 Tweets','13 Tweets','14 Tweets'];
    $scope.series2 = ['People'];
    $scope.data2 = [
        [142005, 18204, 4166, 1292, 425, 160, 66,19,8,4,3,2,3,1]
    ];
    $scope.onClick2 = function (points, evt) {
        console.log(points, evt);
    };
    $scope.datasetOverride2 = [{ yAxisID: 'y-axis-1' }];
    $scope.options2 = {
        responsive: true,
        maintainAspectRatio:true,
        legend: {
            display:true,
            labels:{
                fontColor: 'rgb(255, 99, 132)'
            },
            position:'bottom'
        },
        scales: {
          yAxes: [
            {
              id: 'y-axis-1',
              type: 'linear',
              display: true,
              position: 'left',
              ticks:{
                  min:0,
              }
            }
          ]
        },
        title: {
        display: true,
        text: 'Are many people tweeting a lot about the debate, or are just a few people dominating the discussion with lots of tweets?',
        fontSize:16,
        },
    };
    
//  Q3 data
    
    $ionicModal.fromTemplateUrl('my-modal3.html', {
    scope: $scope,
    animation: 'slide-in-up'
    }).then(function(modal) {
    $scope.modal3 = modal;
    });
    $scope.openModal3 = function() {
      console.log("here")
    $scope.modal3.show();
    };
    $scope.closeModal3 = function() {
    $scope.modal3.hide();
    };
    // Cleanup the modal when we're done with it!
    $scope.$on('$destroy', function() {
    $scope.modal3.remove();
    });
    // Execute action on hide modal
    $scope.$on('modal.hidden', function() {
    // Execute action
    });
    // Execute action on remove modal
    $scope.$on('modal.removed', function() {
    // Execute action
    });
    
    $scope.labels3 =["Very Negative", "Negative", "Neutral", "Positive", "Very Positive"];

    $scope.data3 = [
        [0.5286, 36.9885, 54.2402, 8.0699, 0.1729], //hillary
        [0.5748, 37.674, 54.1048, 7.494, 0.1524] //trump
    ];
    
    $scope.series3 = ['Trump', 'Clinton'];
    
        $scope.datasetOverride3 = [
        { 
          backgroundColor: [
            "#FF6384",
            "#4BC0C0",
        ],
        }
    ];
    
    $scope.options3 = {
        title: {
            display: true,
            text: 'What is the overall sentiment of all the tweets about Hillary Clinton versus the overall sentiment of all the tweets about Donald Trump?',
            fontSize:14,
        },
        responsive: true,
        maintainAspectRatio:true,
        legend: {
            display:true,
            labels:{
                fontColor: 'rgb(255, 99, 132)',
                
            },
            position:'bottom'
            
        },
        
    };
    
//  Q4
    $ionicModal.fromTemplateUrl('my-modal4.html', {
    scope: $scope,
    animation: 'slide-in-up'
    }).then(function(modal) {
    $scope.modal4 = modal;
    });
    $scope.openModal4 = function() {
      console.log("here")
    $scope.modal4.show();
    };
    $scope.closeModal4 = function() {
    $scope.modal4.hide();
    };
    // Cleanup the modal when we're done with it!
    $scope.$on('$destroy', function() {
    $scope.modal4.remove();
    });
    // Execute action on hide modal
    $scope.$on('modal.hidden', function() {
    // Execute action
    });
    // Execute action on remove modal
    $scope.$on('modal.removed', function() {
    // Execute action
    });
    
    $scope.labels4 = ["#debatenight","#politics", "#Debates2016", "#debate", "#debate2016",                         "#TerenceCrutcher","#KeithLamontScott","#birtherism","#DNCleak","#UnlikelyDebateGuests"];
    $scope.data4 = [54264, 15, 17296, 1615, 469, 46, 44, 31, 22 ,20];
    
    $scope.options4 = {
    title: {
        display: true,
        text: 'What are the most popular hashtags that are in the tweets collected?',
        fontSize:16,
    },
    responsive: true,
    maintainAspectRatio:true,
    legend: {
        display:true,
        labels:{
            fontColor: 'rgb(255, 99, 132)',

        },
        position:'bottom'

    },
        
    };
    
    //  Q5
    $ionicModal.fromTemplateUrl('my-modal5.html', {
    scope: $scope,
    animation: 'slide-in-up'
    }).then(function(modal) {
    $scope.modal5 = modal;
    });
    $scope.openModal5 = function() {
      console.log("here")
    $scope.modal5.show();
    };
    $scope.closeModal5 = function() {
    $scope.modal5.hide();
    };
    // Cleanup the modal when we're done with it!
    $scope.$on('$destroy', function() {
    $scope.modal5.remove();
    });
    // Execute action on hide modal
    $scope.$on('modal.hidden', function() {
    // Execute action
    });
    // Execute action on remove modal
    $scope.$on('modal.removed', function() {
    // Execute action
    });
    
  $scope.labels5 = $scope.getTimesTms();
  $scope.series5 = ['Tweets Per Second'];
  $scope.data5 = [
    $scope.getTimesCts(),
  ];
  $scope.onClick5 = function (points, evt) {
    console.log(points, evt);
  };
  $scope.datasetOverride5 = [{ yAxisID: 'y-axis-1' }];
  $scope.options5 = {
    scales: {
      yAxes: [
        {
          id: 'y-axis-1', 
          type: 'linear',
          display: true,
          position: 'left',
          autoSkip:true
        }
      ],
      xAxes:[
          {
              ticks:{
                  maxTicksLimit:20
              }
          }
      ]
        
    },
    title: {
        display: true,
        text: 'At what times were the most tweets created during the debate?',
        fontSize:16,
    },
    responsive: true,
    maintainAspectRatio:true,
    legend: {
        display:true,
        labels:{
            fontColor: 'rgb(255, 99, 132)',

        },
        position:'bottom'

    },
       
  };

})
   
.controller('contactCtrl', function($scope, $http) {
    
    $scope.queryText1 = "";
    $scope.queryText2 = "";
    $scope.queryText3 = "";
    $scope.queryText4 = "";
    $scope.userQuery = "";
    
    
    
    $scope.getSelect1 = function(select){
        console.log(select)
        $scope.queryText2 = select
        $scope.queryText1 = select + " " + $scope.queryText3 + " " + $scope.queryText4 + "?"
    }

    $scope.getSelect2 = function(select){
        console.log(select)
        $scope.queryText3 = select
        $scope.queryText1 = $scope.queryText2 + " " + select + " " + $scope.queryText4 + "?"
    }
    $scope.getSelect3 = function(select){
        console.log(select)
        $scope.queryText4 = select
        $scope.queryText1 = $scope.queryText2 + " " + $scope.queryText3 + " " + select + "?"
    }
    
    $scope.buildQuery = function(){
        console.log("hello world")
        
        $scope.select1 = "SELECT ?i WHERE { "
        $scope.select2 = ""
        $scope.select3 = ""
        $scope.selectType = ""
        console.log($scope.queryText2)
        $scope.sparqlQuery = ""
        
        if($scope.queryText3 == "are the symptoms of"){
            $scope.select2 = " d:" + $scope.queryText4 
            $scope.select3 = " d:hasSymptoms ?i} "
            console.log($scope.select1 + $scope.select2 + $scope.select3)
            $scope.sparqlQuery = $scope.select1 + $scope.select2 + $scope.select3
            $scope.getAnswer( $scope.select1 + $scope.select2 + $scope.select3 )
            
            
        }else if($scope.queryText3 == "body parts are affected by"){
            $scope.select2 = " d:" + $scope.queryText4
            $scope.select3 = " d:isAffected ?i} "
            console.log($scope.select1 + $scope.select2 + $scope.select3)
            $scope.sparqlQuery = $scope.select1 + $scope.select2 + $scope.select3
            $scope.getAnswer( $scope.select1 + $scope.select2 + $scope.select3 )
            
        }else if($scope.queryText3 == "is the cause of"){
            $scope.select2 = " d:" + $scope.queryText4
            $scope.select3 = " d:CausesOf ?i} "
            console.log($scope.select1 + $scope.select2 + $scope.select3)
            $scope.sparqlQuery = $scope.select1 + $scope.select2 + $scope.select3
            $scope.getAnswer( $scope.select1 + $scope.select2 + $scope.select3 )
            
        }else if($scope.queryText3 == "are the types of"){
            $scope.selectType = "?i rdf:type d:" + $scope.queryText4 + "} "
            $scope.sparqlQuery = $scope.select1 + $scope.selectType
            $scope.getAnswer( $scope.select1 + $scope.selectType )
            
        }else{
            console.log("There is an error in query 1")
        }
        
    }
    
    $scope.getAnswer = function(query){
        var link = "http://localhost:8080/QAServer/QueryOntology?query=" + query
        console.log(link)
        $scope.answers = []
        $http.get(link).then(function (response){
            
            for(var i = 0; i <= response.data.total; i++){
                var item = {
                    answer: response.data["Result_" + i]
                };
                $scope.answers.push(item)
            }
            console.log($scope.answers)
        });       
    }
    
})

.controller('chartJS', function($scope){
    $scope.labels = ["January", "February", "March", "April", "May", "June", "July"];
    $scope.series = ['Series A', 'Series B'];
    $scope.data = [
        [65, 59, 80, 81, 56, 55, 40],
        [28, 48, 40, 19, 86, 27, 90]
    ];
})
    