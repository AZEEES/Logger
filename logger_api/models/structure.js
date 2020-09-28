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
    },
    dtype : {
        type : String,
        required : true
    },
    low_lim : {
        type : String,
        required : false
    },
    high_lim : {
        type : String,
        required : false
    }
});

const Structure = module.exports = mongoose.model('Structure', StructureSchema);