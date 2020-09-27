const mongoose = require('mongoose');

const StructureSchema = mongoose.Schema({
    id : {
        type : String,
        required : true
    },
    name : {
        type : String,
        required : true
    },
    parent : {
        type : String, 
        required : true
    }
});

const Structure = module.exports = mongoose.model('Structure', StructureSchema);