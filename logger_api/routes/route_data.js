const express = require('express');
const extend = require('extend');
const router = express.Router();

const Data = require('../models/data');

// getting datas
router.get('/', (req, res, next)=>{
    Data.find((err, datas)=>{
        res.json(datas);
    })
});

//adding datas
router.post('/',(req, res, next)=>{
    let newData = new Data(req.body);
    newData.save((err, data)=>{
        if(err){
            //console.log(err);
            res.json("Error occured in saving : " + err);
        }
        else{
            res.json(data);
        }
    })
})

router.post('/update',(req, res, next)=>{
    data = req.body.array;
    // console.log(data);
    console.log(data.len);
    // for(i=0;i<this.length(data);i++){
    //     console.log(data[i]);
    // }

    // let newData = new Data(req.body);
    // newData.save((err, data)=>{
    //     if(err){
    //         //console.log(err);
    //         res.json("Error occured in saving : " + err);
    //     }
    //     else{
    //         res.json("Success");
    //     }
    // })
})

//deleting datas
router.delete('/',(req, res, next)=>{
    var _id = req.param("id");
    //console.log(_id);
    Data.remove({_id : _id}, (err, result)=>{
        if(err){
            res.json("Error : " + err);
        }
        else{
            res.json("Succesfully deleted");
        }
    } );
})

//deleting datas
router.delete('/delete',(req, res, next)=>{
    var id = req.query.id;
    Data.remove({id : id}, (err, result)=>{
        if(err){
            res.json("Error : " + err);
        }
        else{
            res.json("Succesfully deleted");
        }
    } );
})

//deleting datas
router.delete('/delete_all',(req, res, next)=>{
    Data.remove({}, (err, result)=>{
        if(err){
            res.json("Error : " + err);
        }
        else{
            res.json("Succesfully deleted");
        }
    } );
})


//Fetch all entries by regex 
router.get('/getall', (req, res, next)=>{
    var name = req.body.name;
    Data.find({
        name : {$regex: "^" + name, $options:"i"}
    }, (err, datas) =>{
        if(err){
            res.json("Error")
        }
        else{
            res.json(datas);
        }
    })

})


module.exports = router;