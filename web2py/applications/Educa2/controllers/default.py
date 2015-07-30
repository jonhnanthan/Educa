# -*- coding: utf-8 -*-
# this file is released under public domain and you can use without limitations

#########################################################################
## This is a sample controller
## - index is the default action of any application
## - user is required for authentication and authorization
## - download is for downloading files uploaded in the db (does streaming)
#########################################################################

def index():
    """
    example action using the internationalization operator T and flash
    rendered by views/default/index.html or views/generic.html

    if you need a simple wiki simply replace the two lines below with:
    return auth.wiki()
    """
    response.flash = "%r" % auth.is_logged_in()

    return dict()


def login():
    response.flash = T("Hello ")
    return dict(message=T('Welcome to web2py!'))


@auth.requires_login()
def multi_choice():
    print(request.vars)
    # print(auth.user.username)
    if request.vars:
        corpo = {'Tipo': 'MULTIPLE_CHOICE_EXERCISE',
                 'Alternativa3': request.vars.Alternativa3,
                 'Alternativa2': request.vars.Alternativa2,
                 'Alternativa1': request.vars.Alternativa1,
                 'Resposta': request.vars.Resposta,
                 'Pergunta': request.vars.Pergunta}
        # print(auth.user.username)
        form = SQLFORM(db.atividade)
        form.vars.nome = request.vars.Nome
        form.vars.tipo = db(db.tipo_atividade.nome == "MULTIPLE_CHOICE_EXERCISE").select()[0].id
        form.vars.corpo = str(corpo)
        # form.vars.professor = db(db.professor.nome == "leo").select()[0].id
        id = db.atividade.insert(**dict(form.vars))
        response.flash = 'record inserted'

    return dict()

@auth.requires_login()
def color_match():
    print(request.vars)
    # print(auth.user.username)
    if request.vars:
        corpo = {'Tipo': 'COLOR_MATCH_EXERCISE',
                 'Alternativa3': request.vars.Alternativa3,
                 'Alternativa2': request.vars.Alternativa2,
                 'Alternativa1': request.vars.Alternativa1,
                 'Resposta': request.vars.Color,
                 'Pergunta': request.vars.Pergunta}
        # print(auth.user.username)
        form = SQLFORM(db.atividade)
        form.vars.nome = request.vars.Nome
        form.vars.tipo = db(db.tipo_atividade.nome == "MULTIPLE_CHOICE_EXERCISE").select()[0].id
        form.vars.corpo = str(corpo)
        # form.vars.professor = db(db.professor.nome == "leo").select()[0].id
        id = db.atividade.insert(**dict(form.vars))
        response.flash = 'record inserted'

    return dict()

@auth.requires_login()
def image_match():
    print(request.vars)
    # print(auth.user.username)
    if request.vars:
        corpo = {'Tipo': 'IMAGE_MATCH_EXERCISE',
                 'Resposta': request.vars.Image,
                 'Pergunta': request.vars.Pergunta}
        # print(auth.user.username)
        form = SQLFORM(db.atividade)
        form.vars.nome = request.vars.Nome
        form.vars.tipo = db(db.tipo_atividade.nome == "MULTIPLE_CHOICE_EXERCISE").select()[0].id
        form.vars.corpo = str(corpo)
        # form.vars.professor = db(db.professor.nome == "leo").select()[0].id
        id = db.atividade.insert(**dict(form.vars))
        response.flash = 'record inserted'

    return dict()


@auth.requires_login()
def multi_choice2():
    print(request.vars)
    # print(auth.user.username)
    if request.vars:
        corpo = {'Tipo': 'MULTIPLE_CORRECT_CHOICE_EXERCISE',
                 'Alternativa3': {request.vars.Alternativa3: request.vars.Correta3 or "0"},
                 'Alternativa2': {request.vars.Alternativa2: request.vars.Correta2 or "0"},
                 'Alternativa1': {request.vars.Alternativa1: request.vars.Correta1 or "0"},
                 'Alternativa0': {request.vars.Alternativa0: request.vars.Correta0 or "0"},
                 'Pergunta': request.vars.Pergunta}
        # print(auth.user.username)
        form = SQLFORM(db.atividade)
        form.vars.nome = request.vars.Nome
        form.vars.tipo = db(db.tipo_atividade.nome == "MULTIPLE_CORRECT_CHOICE_EXERCISE").select()[0].id
        form.vars.corpo = str(corpo)
        # /orm.vars.professor = db(db.auth_user.email == auth.user.email).select()[0].id
        id = db.atividade.insert(**dict(form.vars))
        response.flash = 'Atividade Salva com Sucesso'

    return dict()


@auth.requires_login()
def complete():
    print(request.vars)
    # print(auth.user.username)
    if request.vars:
        corpo = {'Tipo': 'COMPLETE_EXERCISE',
                 'Palavra': request.vars.Pergunta,
                 'Resposta': request.vars.Resposta}
        # print(auth.user.username)
        form = SQLFORM(db.atividade)
        form.vars.nome = request.vars.Nome
        form.vars.tipo = db(db.tipo_atividade.nome == "COMPLETE_EXERCISE").select()[0].id
        form.vars.corpo = str(corpo)
        # form.vars.professor = db(db.auth_user.email == auth.user.email).select()[0].id
        print(form.vars)
        id = db.atividade.insert(**dict(form.vars))
        response.flash = 'record inserted'

    return dict()


@auth.requires_login()
def reports():
    return dict()


def create_account():
    form = auth.register()
    # print("request", request.vars)
    # create_professor(form.validate(), form)

    return dict(form=form)


def create_professor(create, form):
    if create:
        print("vars")
        print(form.vars)

        form_professor = SQLFORM(db.professor)
        form_professor.vars.nome = form.vars.first_name + form.vars.last_name
        form_professor.vars.username = form.vars.email
        print(form_professor.vars)
        # form.drop("password_two")
        user_id = db.auth_user.insert(**dict(first_name=form.vars.first_name,
                                             last_name=form.vars.last_name,
                                             email=form.vars.email,
                                             password=form.vars.password))

        id = db.professor.insert(**dict(form_professor.vars))
        response.flash = "form accepted"


def user():
    """
    exposes:
    http://..../[app]/default/user/login
    http://..../[app]/default/user/logout
    http://..../[app]/default/user/register
    http://..../[app]/default/user/profile
    http://..../[app]/default/user/retrieve_password
    http://..../[app]/default/user/change_password
    http://..../[app]/default/user/manage_users (requires membership in
    use @auth.requires_login()
        @auth.requires_membership('group name')
        @auth.requires_permission('read','table name',record_id)
    to decorate functions that need access control
    """
    return dict(form=auth())


@cache.action()
def download():
    """
    allows downloading of uploaded files
    http://..../[app]/default/download/[filename]
    """
    return response.download(request, db)


def call():
    """
    exposes services. for example:
    http://..../[app]/default/call/jsonrpc
    decorate with @services.jsonrpc the functions to expose
    supports xml, json, xmlrpc, jsonrpc, amfrpc, rss, csv
    """
    return service()


@request.restful()
# @auth.requires_login()
def api():
    response.view = 'generic.' + request.extension
    print(response.view)

    def GET(*args, **vars):
        patterns = 'auto'
        parser = db.parse_as_rest(patterns, args, vars)
        if parser.status == 200:
            return dict(content=parser.response)
        else:
            raise HTTP(parser.status, parser.error)

    def POST(table_name, **vars):
        print(vars, db[table_name])
        return db[table_name].validate_and_insert(**vars)

    def PUT(table_name, record_id, **vars):
        return db(db[table_name]._id == record_id).update(**vars)

    def DELETE(table_name, record_id):
        return db(db[table_name]._id == record_id).delete()

    return dict(GET=GET, POST=POST, PUT=PUT, DELETE=DELETE)


