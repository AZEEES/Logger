const express = require('express');
const extend = require('extend');
const router = express.Router();

const User = require('../models/user');

// getting users
router.get('/', (req, res, next)=>{
    User.find((err, users)=>{
        res.json(users);
    })
});

//adding users
router.post('/',(req, res, next)=>{
    let newUser = new User(req.body);
    newUser.save((err, user)=>{
        if(err){
            //console.log(err);
            res.json("Error occured in saving : " + err);
        }
        else{
            res.json(user);
        }
    })
});

router.post('/check_valid',(req, res, next)=>{
    let phone = req.body.phone;
    let password = req.body.password;
    User.findOne({phone:phone, password:password},(err, user)=>{
        if(err){
            res.json(err);
        }
        else{
            res.json(user);
        }
    })
});

module.exports = router;