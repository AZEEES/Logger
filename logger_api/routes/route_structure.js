const express = require('express');
const extend = require('extend');
const router = express.Router();

const Structure = require('../models/structure');

// getting structures
router.get('/', (req, res, next)=>{
    Structure.find((err, structures)=>{
        res.json(structures);
    })
});

//adding structures
router.post('/',(req, res, next)=>{
    let newStructure = new Structure(req.body);
    newStructure.save((err, structure)=>{
        if(err){
            //console.log(err);
            res.json("Error occured in saving : " + err);
        }
        else{
            res.json(structure);
        }
    })
})

//deleting structures
router.delete('/',(req, res, next)=>{
    var _id = req.param("id");
    //console.log(_id);
    Structure.remove({_id : _id}, (err, result)=>{
        if(err){
            res.json("Error : " + err);
        }
        else{
            res.json("Succesfully deleted");
        }
    } );
})

//Fetch all entries by regex 
router.post('/getchilds', (req, res, next)=>{
    var parent = req.query.parent;
    console.log(req)
    Structure.find({
        // parent : {$regex: "^" + parent, $options:"i"}
        parent : parent
    }, (err, structures) =>{
        if(err){
            res.json("Error")
        }
        else{
            res.json(structures);
        }
    })

})

//Fetch all entries by regex 
router.get('/getall', (req, res, next)=>{
    var name = req.body.name;
    Structure.find({
        name : {$regex: "^" + name, $options:"i"}
    }, (err, structures) =>{
        if(err){
            res.json("Error")
        }
        else{
            res.json(structures);
        }
    })

})


module.exports = router;