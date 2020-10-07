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

//updating structures
router.get('/update',(req, res, next)=>{
    console.log("Update function called");
    var id = req.query.id
    var structure = req.query.structure;
    var structure = JSON.parse(structure);
    // console.log(structure);
    Structure.findByIdAndUpdate(id, structure, (err, result)=>{
        if(err){
            res.json("Error : " + err);
        }
        else{
            res.json("success");
        }
    })
    // res.json("success");
})

//updating structures
router.get('/add',(req, res, next)=>{
    var id = req.query.id
    var structure = req.query.structure;
    var structure = JSON.parse(structure);  
    let newStructure = new Structure(structure);
    newStructure.save((err, structure)=>{
        if(err){
            //console.log(err);
            res.json("Error occured in saving : " + err);
        }
        else{
            res.json("success");
        }
    })
})

router.get('/delete', (req, res, next)=>{
    var _id = req.query.id;
    Structure.remove({_id : _id}, (err, result)=>{
        if(err){
            res.json("Error : " + err);
        }
        else{
            res.json("success");
        }
    } );

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

//deleting structures
router.delete('/delete',(req, res, next)=>{
    var id = req.query.id;
    Structure.remove({id : id}, (err, result)=>{
        if(err){
            res.json("Error : " + err);
        }
        else{
            res.json("Succesfully deleted");
        }
    } );
})

//deleting structures
router.delete('/delete_all',(req, res, next)=>{
    Structure.remove({}, (err, result)=>{
        if(err){
            res.json("Error : " + err);
        }
        else{
            res.json("Succesfully deleted");
        }
    } );
})

//Fetch all entries by regex 
router.get('/getchilds', (req, res, next)=>{
    var parent = req.query.parent;
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